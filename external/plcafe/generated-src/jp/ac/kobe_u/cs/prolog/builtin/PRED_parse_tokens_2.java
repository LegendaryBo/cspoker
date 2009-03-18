package jp.ac.kobe_u.cs.prolog.builtin;
import jp.ac.kobe_u.cs.prolog.lang.*;
/*
 This file is generated by Prolog Cafe.
 PLEASE DO NOT EDIT!
*/
/**
 <code>parse_tokens/2</code> defined in builtins.pl<br>
 @author Mutsunori Banbara (banbara@kobe-u.ac.jp)
 @author Naoyuki Tamura (tamura@kobe-u.ac.jp)
 @version 1.0
*/
class PRED_parse_tokens_2 extends Predicate {
    static SymbolTerm s1 = SymbolTerm.makeSymbol(":", 2);
    static SymbolTerm s2 = SymbolTerm.makeSymbol("jp.ac.kobe_u.cs.prolog.builtin");
    static SymbolTerm s3 = SymbolTerm.makeSymbol("$tokens", 1);
    static IntegerTerm si4 = new IntegerTerm(1201);
    static SymbolTerm s5 = SymbolTerm.makeSymbol(".");
    static SymbolTerm s6 = SymbolTerm.makeSymbol("[]");
    static ListTerm s7 = new ListTerm(s5, s6);

    public Term arg1, arg2;

    public PRED_parse_tokens_2(Term a1, Term a2, Predicate cont) {
        arg1 = a1;
        arg2 = a2;
        this.cont = cont;
    }

    public PRED_parse_tokens_2(){}

    public void setArgument(Term[] args, Predicate cont) {
        arg1 = args[0];
        arg2 = args[1];
        this.cont = cont;
    }

    public int arity() { return 2; }

    public String toString() {
        return "parse_tokens(" + arg1 + "," + arg2 + ")";
    }

    public Predicate exec(Prolog engine) {
    // parse_tokens(A,B):-retractall('$tokens'(C)),assertz('$tokens'(B)),'$parse_tokens'(A,1201,B,['.']),retract('$tokens'(B)),!
        engine.setB0();
        Term a1, a2, a3, a4, a5, a6, a7, a8, a9;
        Predicate p1, p2, p3, p4;
        a1 = arg1;
        a2 = arg2;
    // parse_tokens(A,B):-['$get_level'(C),retractall('jp.ac.kobe_u.cs.prolog.builtin':'$tokens'(D)),assertz('jp.ac.kobe_u.cs.prolog.builtin':'$tokens'(B)),'$parse_tokens'(A,1201,B,['.']),retract('jp.ac.kobe_u.cs.prolog.builtin':'$tokens'(B)),'$cut'(C)]
        a3 = new VariableTerm(engine);
        //START inline expansion of $get_level(a(3))
        if (! a3.unify(new IntegerTerm(engine.B0), engine.trail)) {
            return engine.fail();
        }
        //END inline expansion
        Term[] y1 = {new VariableTerm(engine)};
        a4 = new StructureTerm(s3, y1);
        Term[] y2 = {s2, a4};
        a5 = new StructureTerm(s1, y2);
        Term[] y3 = {a2};
        a6 = new StructureTerm(s3, y3);
        Term[] y4 = {s2, a6};
        a7 = new StructureTerm(s1, y4);
        Term[] y5 = {a2};
        a8 = new StructureTerm(s3, y5);
        Term[] y6 = {s2, a8};
        a9 = new StructureTerm(s1, y6);
        p1 = new PRED_$cut_1(a3, cont);
        p2 = new PRED_retract_1(a9, p1);
        p3 = new PRED_$parse_tokens_4(a1, si4, a2, s7, p2);
        p4 = new PRED_assertz_1(a7, p3);
        return new PRED_retractall_1(a5, p4);
    }
}
