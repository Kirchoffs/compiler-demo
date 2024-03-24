package org.syh.demo.learning.visitor;

import org.syh.demo.learning.visitor.Pastry.Beignet;
import org.syh.demo.learning.visitor.Pastry.Cruller;

interface PastryVisitor {
    default void visit(Pastry pastry) {
        pastry.accept(this);
    }
    void visit(Beignet beignet);
    void visit(Cruller cruller);
}
