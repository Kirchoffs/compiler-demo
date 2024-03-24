package org.syh.demo.learning.visitor;

public abstract class Pastry {
    abstract void accept(PastryVisitor visitor);

    public static class Beignet extends Pastry {
        public int id;

        public Beignet(int id) {
            this.id = id;
        }

        @Override
        void accept(PastryVisitor visitor) {
            visitor.visit(this);
        }
    }

    public static class Cruller extends Pastry {
        public int id;

        public Cruller(int id) {
            this.id = id;
        }

        @Override
        void accept(PastryVisitor visitor) {
            visitor.visit(this);
        }
    }
}
