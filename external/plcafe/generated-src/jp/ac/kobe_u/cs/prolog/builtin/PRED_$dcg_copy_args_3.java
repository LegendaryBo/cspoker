package jp.ac.kobe_u.cs.prolog.builtin;
import jp.ac.kobe_u.cs.prolog.lang.*;
/*
 This file is generated by Prolog Cafe.
 PLEASE DO NOT EDIT!
*/
/**
 <code>'$dcg_copy_args'/3</code> defined in builtins.pl<br>
 @author Mutsunori Banbara (banbara@kobe-u.ac.jp)
 @author Naoyuki Tamura (tamura@kobe-u.ac.jp)
 @version 1.0
*/
class PRED_$dcg_copy_args_3 extends Predicate {
    static IntegerTerm si1 = new IntegerTerm(0);
    static IntegerTerm si2 = new IntegerTerm(1);
    static Predicate _$dcg_copy_args_3_var = new PRED_$dcg_copy_args_3_var();
    static Predicate _$dcg_copy_args_3_var_1 = new PRED_$dcg_copy_args_3_var_1();
    static Predicate _$dcg_copy_args_3_1 = new PRED_$dcg_copy_args_3_1();
    static Predicate _$dcg_copy_args_3_2 = new PRED_$dcg_copy_args_3_2();

    public Term arg1, arg2, arg3;

    public PRED_$dcg_copy_args_3(Term a1, Term a2, Term a3, Predicate cont) {
        arg1 = a1;
        arg2 = a2;
        arg3 = a3;
        this.cont = cont;
    }

    public PRED_$dcg_copy_args_3(){}

    public void setArgument(Term[] args, Predicate cont) {
        arg1 = args[0];
        arg2 = args[1];
        arg3 = args[2];
        this.cont = cont;
    }

    public int arity() { return 3; }

    public String toString() {
        return "$dcg_copy_args(" + arg1 + "," + arg2 + "," + arg3 + ")";
    }

    public Predicate exec(Prolog engine) {
        engine.aregs[1] = arg1;
        engine.aregs[2] = arg2;
        engine.aregs[3] = arg3;
        engine.cont = cont;
        engine.setB0();
        return engine.switch_on_term(_$dcg_copy_args_3_var, _$dcg_copy_args_3_var, _$dcg_copy_args_3_2, _$dcg_copy_args_3_2, _$dcg_copy_args_3_2, _$dcg_copy_args_3_2);
    }
}

class PRED_$dcg_copy_args_3_var extends PRED_$dcg_copy_args_3 {
    public Predicate exec(Prolog engine) {
        return engine.jtry(_$dcg_copy_args_3_1, _$dcg_copy_args_3_var_1);
    }
}

class PRED_$dcg_copy_args_3_var_1 extends PRED_$dcg_copy_args_3 {
    public Predicate exec(Prolog engine) {
        return engine.trust(_$dcg_copy_args_3_2);
    }
}

class PRED_$dcg_copy_args_3_1 extends PRED_$dcg_copy_args_3 {
    public Predicate exec(Prolog engine) {
    // '$dcg_copy_args'(0,A,B):-!
        Term a1, a2, a3;
        Predicate cont;
        a1 = engine.aregs[1];
        a2 = engine.aregs[2];
        a3 = engine.aregs[3];
        cont = engine.cont;
    // '$dcg_copy_args'(0,A,B):-['$neck_cut']
        a1 = a1.dereference();
        if (a1.isInteger()){
            if (((IntegerTerm) a1).intValue() != 0)
                return engine.fail();
        } else if (a1.isVariable()){
            ((VariableTerm) a1).bind(si1, engine.trail);
        } else {
            return engine.fail();
        }
        //START inline expansion of $neck_cut
        engine.neckCut();
        //END inline expansion
        return cont;
    }
}

class PRED_$dcg_copy_args_3_2 extends PRED_$dcg_copy_args_3 {
    public Predicate exec(Prolog engine) {
    // '$dcg_copy_args'(A,B,C):-arg(A,B,D),arg(A,C,D),E is A-1,'$dcg_copy_args'(E,B,C)
        Term a1, a2, a3, a4, a5;
        Predicate p1, p2, p3;
        Predicate cont;
        a1 = engine.aregs[1];
        a2 = engine.aregs[2];
        a3 = engine.aregs[3];
        cont = engine.cont;
    // '$dcg_copy_args'(A,B,C):-[arg(A,B,D),arg(A,C,D),'$minus'(A,1,E),'$dcg_copy_args'(E,B,C)]
        a4 = new VariableTerm(engine);
        a5 = new VariableTerm(engine);
        p1 = new PRED_$dcg_copy_args_3(a5, a2, a3, cont);
        p2 = new PRED_$minus_3(a1, si2, a5, p1);
        p3 = new PRED_arg_3(a1, a3, a4, p2);
        return new PRED_arg_3(a1, a2, a4, p3);
    }
}