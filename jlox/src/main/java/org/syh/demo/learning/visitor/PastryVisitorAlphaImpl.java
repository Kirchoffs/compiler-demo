package org.syh.demo.learning.visitor;

import org.syh.demo.learning.visitor.Pastry.Beignet;
import org.syh.demo.learning.visitor.Pastry.Cruller;

public class PastryVisitorAlphaImpl implements PastryVisitor {
    @Override
    public void visit(Beignet beignet) {
        System.out.println("Alpha Beignet with id " + beignet.id);
    }

    @Override
    public void visit(Cruller cruller) {
        System.out.println("Alpha Cruller with id " + cruller.id);
    }
}
