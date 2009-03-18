package jp.ac.kobe_u.cs.prolog.builtin;
import jp.ac.kobe_u.cs.prolog.lang.*;
/*
 This file is generated by Prolog Cafe.
 PLEASE DO NOT EDIT!
*/
/**
 <code>^ /2</code> defined in builtins.pl<br>
 @author Mutsunori Banbara (banbara@kobe-u.ac.jp)
 @author Naoyuki Tamura (tamura@kobe-u.ac.jp)
 @version 1.0
*/
public class PRED_$005E_2 extends Predicate {
    static SymbolTerm s1 = SymbolTerm.makeSymbol(":", 2);
    static SymbolTerm s2 = SymbolTerm.makeSymbol("jp.ac.kobe_u.cs.prolog.builtin");

    public Term arg1, arg2;

    public PRED_$005E_2(Term a1, Term a2, Predicate cont) {
        arg1 = a1;
        arg2 = a2;
        this.cont = cont;
    }

    public PRED_$005E_2(){}

    public void setArgument(Term[] args, Predicate cont) {
        arg1 = args[0];
        arg2 = args[1];
        this.cont = cont;
    }

    public int arity() { return 2; }

    public String toString() {
        return "^(" + arg1 + "," + arg2 + ")";
    }

    public Predicate exec(Prolog engine) {
    // A^B:-call(B)
        engine.setB0();
        Term a1, a2, a3;
        a1 = arg1;
        a2 = arg2;
    // A^B:-[call('jp.ac.kobe_u.cs.prolog.builtin':B)]
        Term[] y1 = {s2, a2};
        a3 = new StructureTerm(s1, y1);
        return new PRED_call_1(a3, cont);
    }
}
