package org.syh.demo.learning.visitor;

import org.syh.demo.learning.visitor.Pastry.Beignet;
import org.syh.demo.learning.visitor.Pastry.Cruller;

public class Main {
    public static void main(String[] args) {
        PastryVisitor alphaVisitor = new PastryVisitorAlphaImpl();
        PastryVisitor betaVisitor = new PastryVisitorBetaImpl();

        Pastry beignet = new Beignet(42);
        beignet.accept(alphaVisitor);
        beignet.accept(betaVisitor);

        Pastry cruller = new Cruller(84);
        cruller.accept(alphaVisitor);
        cruller.accept(betaVisitor);

        PastryVisitor gammaVisitor = new PastryVisitorGammaImpl();
        gammaVisitor.visit(beignet);
        gammaVisitor.visit(cruller);
    }
}
