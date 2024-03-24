package org.syh.demo.learning.visitor;

import org.syh.demo.learning.visitor.Pastry.Beignet;
import org.syh.demo.learning.visitor.Pastry.Cruller;

public class PastryVisitorBetaImpl implements PastryVisitor {
    @Override
    public void visit(Beignet beignet) {
        System.out.println("Beta Beignet with id " + beignet.id);
    }

    @Override
    public void visit(Cruller cruller) {
        System.out.println("Beta Cruller with id " + cruller.id);
    }
}
