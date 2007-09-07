package org.cspoker.server.sockets.runnables;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;
import org.cspoker.server.common.authentication.XmlFileAuthenticator;
import org.cspoker.server.sockets.ClientContext;
import org.cspoker.server.sockets.security.SocketsAuthenticator;
import org.cspoker.server.sockets.threading.Prioritizable;

public class WaitForIO implements Runnable, Prioritizable{

    private final static Logger logger = Logger.getLogger(WaitForIO.class);

    private final ThreadPoolExecutor executor;
    private final Selector selector;
    private ServerSocketChannel server;
    
    public final static int bufferSize = 1024;
    private final ByteBuffer buffer;
    private final ByteBuffer filteredBuffer;

    private final Charset charset;
    private final CharsetDecoder decoder;

    private final SocketsAuthenticator auth;

    public WaitForIO(ThreadPoolExecutor executor, Selector selector, ServerSocketChannel server) {
	this.executor = executor;
	this.selector = selector;
	this.server =server;


	buffer = ByteBuffer.allocateDirect(bufferSize);
	filteredBuffer = ByteBuffer.allocateDirect(bufferSize);

	charset=Charset.forName("UTF-8");
	decoder = charset.newDecoder();

	auth = new SocketsAuthenticator(new XmlFileAuthenticator());

    }

    public void run() {
	waitForWork();
	executor.execute(this);
    }

    public int getPriority() {
	return -1;
    }

    private void waitForWork(){
	// Waiting for events
	try {
	    selector.select();
	    // Get keys
	    Set<SelectionKey> keys = selector.selectedKeys();
	    Iterator<SelectionKey> i = keys.iterator();

	    // For each keys...
	    while(i.hasNext()) {
		SelectionKey key = (SelectionKey)i.next();

		int kro = key.readyOps();


		if((kro & SelectionKey.OP_READ) == SelectionKey.OP_READ){
		    readSocket(key);
		    logger.trace("read from socket");
		}
		if((kro & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE){
		    getContext(key, (SocketChannel) key.channel()).writeBufferToClient();
		    logger.trace("wrote data to socket");
		}
		if((kro & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
		    acceptConnection();
		    logger.trace("accepted new connection");
		}
		i.remove();			// remove the key

	    }
	} catch (IOException e) {
	    //no op
	    logger.error(e.getMessage());
	    e.printStackTrace();
	}


    }

    private void readSocket(SelectionKey key) throws IOException {
	SocketChannel client = (SocketChannel) key.channel();

	// Clear the buffer and read bytes from socket
	buffer.clear();
	int numBytesRead = client.read(buffer);

	if (numBytesRead == -1) {
	    // No more bytes can be read from the channel
	    client.close();
	} else {
	    // To read the bytes, flip the buffer
	    buffer.flip();
	    ClientContext context = getContext(key, client);
	    StringBuilder stringBuilder = context.getBuffer();

	    while (buffer.hasRemaining()) {
		boolean hasEnded = filterUntilEndNode();

		CharBuffer decoded = decoder.decode(filteredBuffer);
		stringBuilder.append(decoded);
		if(hasEnded){
		    endNode(stringBuilder, context);
		}
	    }
	}

    }

    private ClientContext getContext(SelectionKey key, SocketChannel client) {
	ClientContext context = (ClientContext)(key.attachment());
	if(context==null){
	    context = new ClientContext(client, selector);
	    key.attach(context);
	}
	return context;

    }

    private boolean filterUntilEndNode(){
	filteredBuffer.clear();
	while (buffer.hasRemaining()) {
	    byte b = buffer.get();
	    if(b==0){
		filteredBuffer.flip();
		return true;
	    }else{
		filteredBuffer.put(b);
	    }

	}
	filteredBuffer.flip();
	return false;
    }

    private void acceptConnection() throws IOException{
	// get client socket channel
	SocketChannel client = server.accept();
	// Non Blocking I/O
	client.configureBlocking(false);
	// recording to the selector (reading)
	client.register(selector, SelectionKey.OP_READ);
    }

    private void endNode(StringBuilder stringBuilder, ClientContext context){
	executor.submit(new ProcessXML(stringBuilder.toString(), context, auth));
	stringBuilder.setLength(0);
	logger.debug("ended an xml node");
    }
}



