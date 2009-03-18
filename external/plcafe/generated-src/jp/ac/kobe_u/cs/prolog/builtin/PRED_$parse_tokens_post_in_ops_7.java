package jp.ac.kobe_u.cs.prolog.builtin;
import jp.ac.kobe_u.cs.prolog.lang.*;
/*
 This file is generated by Prolog Cafe.
 PLEASE DO NOT EDIT!
*/
/**
 <code>'$parse_tokens_post_in_ops'/7</code> defined in builtins.pl<br>
 @author Mutsunori Banbara (banbara@kobe-u.ac.jp)
 @author Naoyuki Tamura (tamura@kobe-u.ac.jp)
 @version 1.0
*/
class PRED_$parse_tokens_post_in_ops_7 extends Predicate {
    static Predicate _$parse_tokens_post_in_ops_7_sub_1 = new PRED_$parse_tokens_post_in_ops_7_sub_1();
    static Predicate _$parse_tokens_post_in_ops_7_1 = new PRED_$parse_tokens_post_in_ops_7_1();
    static Predicate _$parse_tokens_post_in_ops_7_2 = new PRED_$parse_tokens_post_in_ops_7_2();

    public Term arg1, arg2, arg3, arg4, arg5, arg6, arg7;

    public PRED_$parse_tokens_post_in_ops_7(Term a1, Term a2, Term a3, Term a4, Term a5, Term a6, Term a7, Predicate cont) {
        arg1 = a1;
        arg2 = a2;
        arg3 = a3;
        arg4 = a4;
        arg5 = a5;
        arg6 = a6;
        arg7 = a7;
        this.cont = cont;
    }

    public PRED_$parse_tokens_post_in_ops_7(){}

    public void setArgument(Term[] args, Predicate cont) {
        arg1 = args[0];
        arg2 = args[1];
        arg3 = args[2];
        arg4 = args[3];
        arg5 = args[4];
        arg6 = args[5];
        arg7 = args[6];
        this.cont = cont;
    }

    public int arity() { return 7; }

    public String toString() {
        return "$parse_tokens_post_in_ops(" + arg1 + "," + arg2 + "," + arg3 + "," + arg4 + "," + arg5 + "," + arg6 + "," + arg7 + ")";
    }

    public Predicate exec(Prolog engine) {
        engine.aregs[1] = arg1;
        engine.aregs[2] = arg2;
        engine.aregs[3] = arg3;
        engine.aregs[4] = arg4;
        engine.aregs[5] = arg5;
        engine.aregs[6] = arg6;
        engine.aregs[7] = arg7;
        engine.cont = cont;
        engine.setB0();
        return engine.jtry(_$parse_tokens_post_in_ops_7_1, _$parse_tokens_post_in_ops_7_sub_1);
    }
}

class PRED_$parse_tokens_post_in_ops_7_sub_1 extends PRED_$parse_tokens_post_in_ops_7 {
    public Predicate exec(Prolog engine) {
        return engine.trust(_$parse_tokens_post_in_ops_7_2);
    }
}

class PRED_$parse_tokens_post_in_ops_7_1 extends PRED_$parse_tokens_post_in_ops_7 {
    public Predicate exec(Prolog engine) {
    // '$parse_tokens_post_in_ops'(A,B,C,D,E,F,G):-'$parse_tokens_skip_spaces'(F,H),'C'(H,I,J),'$parse_tokens_op'(I,A,B,C,K,L,J,M),'$parse_tokens_post_in_ops'(A,K,L,D,E,M,G)
        Term a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13;
        Predicate p1, p2, p3;
        Predicate cont;
        a1 = engine.aregs[1];
        a2 = engine.aregs[2];
        a3 = engine.aregs[3];
        a4 = engine.aregs[4];
        a5 = engine.aregs[5];
        a6 = engine.aregs[6];
        a7 = engine.aregs[7];
        cont = engine.cont;
    // '$parse_tokens_post_in_ops'(A,B,C,D,E,F,G):-['$parse_tokens_skip_spaces'(F,H),'C'(H,I,J),'$parse_tokens_op'(I,A,B,C,K,L,J,M),'$parse_tokens_post_in_ops'(A,K,L,D,E,M,G)]
        a8 = new VariableTerm(engine);
        a9 = new VariableTerm(engine);
        a10 = new VariableTerm(engine);
        a11 = new VariableTerm(engine);
        a12 = new VariableTerm(engine);
        a13 = new VariableTerm(engine);
        p1 = new PRED_$parse_tokens_post_in_ops_7(a1, a11, a12, a4, a5, a13, a7, cont);
        p2 = new PRED_$parse_tokens_op_8(a9, a1, a2, a3, a11, a12, a10, a13, p1);
        p3 = new PRED_C_3(a8, a9, a10, p2);
        return new PRED_$parse_tokens_skip_spaces_2(a6, a8, p3);
    }
}

class PRED_$parse_tokens_post_in_ops_7_2 extends PRED_$parse_tokens_post_in_ops_7 {
    public Predicate exec(Prolog engine) {
    // '$parse_tokens_post_in_ops'(A,B,C,B,C,D,E):-C=<A,E=D
        Term a1, a2, a3, a4, a5, a6, a7;
        Predicate cont;
        a1 = engine.aregs[1];
        a2 = engine.aregs[2];
        a3 = engine.aregs[3];
        a4 = engine.aregs[4];
        a5 = engine.aregs[5];
        a6 = engine.aregs[6];
        a7 = engine.aregs[7];
        cont = engine.cont;
    // '$parse_tokens_post_in_ops'(A,B,C,B,C,D,E):-['$less_or_equal'(C,A),'$unify'(E,D)]
        if (! a2.unify(a4, engine.trail))
            return engine.fail();
        if (! a3.unify(a5, engine.trail))
            return engine.fail();
        //START inline expansion of $less_or_equal(a(3),a(1))
        try {
            if (Arithmetic.evaluate(a3).arithCompareTo(Arithmetic.evaluate(a1)) > 0) {
                return engine.fail();
            }
        } catch (BuiltinException e) {
            e.goal = this;
            throw e;
        }
        //END inline expansion
        //START inline expansion of $unify(a(7),a(6))
        if (! a7.unify(a6, engine.trail)) {
            return engine.fail();
        }
        //END inline expansion
        return cont;
    }
}
