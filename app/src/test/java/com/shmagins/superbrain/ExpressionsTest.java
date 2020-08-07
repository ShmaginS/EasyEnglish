package com.shmagins.superbrain;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import kotlin.ranges.IntRange;

import static com.shmagins.superbrain.Expressions.getOperationListFromString;
import static org.junit.Assert.*;

public class ExpressionsTest {
    Operand<Integer> op1;
    Operand<Integer> op2;
    Operand<Integer> op3;
    Operand<Integer> op4;
    Operand<Integer> op5;

    @Before
    public void setup() {
        op1 = new Value<>(1);
        op2 = new Value<>(2);
        op3 = new Value<>(3);
        op4 = new Value<>(4);
        op5 = new Value<>(5);
    }

    @Test
    public void valueTest() {
        assertEquals(op1.getValue(), (Integer) 1);
    }

    @Test
    public void expressionTest() {
        Expression<Integer> expr1 = new Expression<>(Operation.PLUS, op1, op1);
        Expression<Integer> expr2 = new Expression<>(Operation.MINUS, op1, op1);
        Expression<Integer> expr3 = new Expression<>(Operation.MULTIPLY, op1, op1);
        Expression<Integer> expr4 = new Expression<>(Operation.DIVIDE, op1, op1);
        assertEquals(expr1.toString(), "1 + 1");
        assertEquals(expr2.toString(), "1 - 1");
        assertEquals(expr1.getValue(), (Integer) 2);
        assertEquals(expr2.getValue(), (Integer) 0);
        assertEquals(expr3.getValue(), (Integer) 1);
        assertEquals(expr4.getValue(), (Integer) 1);
    }

    @Test
    public void nestedExpressionTest() {
        Expression<Integer> expr1 = new Expression<>(Operation.PLUS, op1, op2);
        Expression<Integer> expr2 = new Expression<>(Operation.MULTIPLY, op3, expr1);
        Expression<Integer> expr3 = new Expression<>(Operation.DIVIDE, expr2, op3);

        assertEquals((Integer) 9, expr2.getValue());
        assertEquals((Integer) 3, expr3.getValue());
    }

    @Test
    public void expressionDecoratorTest() {
        Operand<Integer> op2 = new Inversion<>(op1);
        Operand<Integer> op3 = new Inversion<>(op2);

        assertEquals((Integer) (-1), op2.getValue());
        assertEquals("-1", op2.toString());

        assertEquals((Integer) (1), op3.getValue());
        assertEquals("1", op3.toString());

        Expression<Integer> expr1 = new Expression<>(Operation.PLUS, op1, op5);
    }

    @Test
    public void randTest(){
        for (int i = 0; i < 100; i++) {
            Integer random = Expressions.rand(0, 10);
            assertTrue(random >= 0);
            assertTrue(random <= 10);
        }
    }

    @Test
    public void createExpressionTest() {
        for (int i = 0; i < 100; i++) {
            OperationDescriptor<Integer> op = new OperationDescriptor<>(Operation.PLUS, 0, 100, 0, 100, 0, 200);
            Expression<Integer> expr = op.createExpression();
            assertTrue(expr.getValue() <= 200);
        }
    }

    @Test
    public void getOperationListFromStringTest(){
        List<Operation> ops = getOperationListFromString("PLUS");
        assertEquals(ops.get(0), Operation.PLUS);
        ops = getOperationListFromString("PLUS MINUS");
        assertEquals(ops.get(0), Operation.PLUS);
        assertEquals(ops.get(1), Operation.MINUS);
        ops = getOperationListFromString(" PLUS MINUS DIVIDE ");
        assertEquals(ops.get(0), Operation.PLUS);
        assertEquals(ops.get(1), Operation.MINUS);
        assertEquals(ops.get(2), Operation.DIVIDE);
    }
}
