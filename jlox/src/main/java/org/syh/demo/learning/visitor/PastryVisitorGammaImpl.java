package org.syh.demo.learning.visitor;

import org.syh.demo.learning.visitor.Pastry.Beignet;
import org.syh.demo.learning.visitor.Pastry.Cruller;

public class PastryVisitorGammaImpl implements PastryVisitor {
    @Override
    public void visit(Beignet beignet) {
        System.out.println("Gamma Beignet with id " + beignet.id);
    }

    @Override
    public void visit(Cruller cruller) {
        System.out.println("Gamma Cruller with id " + cruller.id);
    }
    
}
