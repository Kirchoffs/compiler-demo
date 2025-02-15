# Notes

## Visitor Pattern

Two way to apply visitor pattern:

### Element directly accepts visitor
- `element.accept(visitor)`;
- then inside Element sub-class `accept` method, `visitor.visit(this)`, or sometimes `visitor.visitSubElement(this)`;
- `this` contains the exact type of Element, so visitor can do specific operation.

### Visitor visits element
- `visitor.visit(element);`
- then inside Visitor sub-class `visit` method, `element.accept(this)`;
- then inside Element sub-class `accept` method , `visitor.visit(this)`, or sometimes `visitor.visitSubElement(this)`;
- `this` contains the exact type of Element, so visitor can do specific operation.

## Visitor Pattern with Additional Context Parameter
```java
public interface Element {
    void accept(Visitor visitor, Context context);
}

public class ConcreteAlphaElement implements Element {
    @Override
    public void accept(Visitor visitor, Context context) {
        visitor.visit(this, context);
    }

    // Another form:
    // 
    // @Override
    // public void accept(Visitor visitor, Context context) {
    //     visitor.visitAlphaElement(this, context);
    // }
}

public class ConcreteBetaElement implements Element {
    @Override
    public void accept(Visitor visitor, Context context) {
        visitor.visit(this, context);
    }

    // Another form:
    // 
    // @Override
    // public void accept(Visitor visitor, Context context) {
    //     visitor.visitBetaElement(this, context);
    // }
}
```

```java
public interface Visitor {
    default void visit(Element element, Context context) {
        element.accept(this, context);
    }

    void visit(ConcreteAlphaElement element, Context context);
    void visit(ConcreteBetaElement element, Context context);

    // Another form:
    //
    // void visitAlphaElement(ConcreteAlphaElement element, Context context);
    // void visitBetaElement(ConcreteBetaElement element, Context context);
}

public class ConcreteFirstVisitor implements Visitor {
    public void visit(ConcreteAlphaElement element, Context context) {
        // do something with element and context
    }

    public void visit(ConcreteBetaElement element, Context context) {
        // do something with element and context
    }

    // Another form:
    //
    // public void visitAlphaElement(ConcreteAlphaElement element, Context context) {
    //     ...
    // }
    //
    // public void visitBetaElement(ConcreteBetaElement element, Context context) {
    //     ...
    // }
}

public class ConcreteSecondVisitor implements Visitor {
    public void visit(ConcreteAlphaElement element, Context context) {
        // do something with element and context
    }

    public void visit(ConcreteBetaElement element, Context context) {
        // do something with element and context
    }

    // Another form:
    //
    // public void visitAlphaElement(ConcreteAlphaElement element, Context context) {
    //     ...
    // }
    //
    // public void visitBetaElement(ConcreteBetaElement element, Context context) {
    //     ...
    // }
}
```

```java
Element alphaElement = new ConcreteAlphaElement();
Element betaElement = new ConcreteBetaElement();

Visitor firstVisitor = new ConcreteFirstVisitor();
Visitor secondVisitor = new ConcreteSecondVisitor();

Context context = new Context();

alphaElement.accept(firstVisitor, context);
alphaElement.accept(secondVisitor, context);

firstVisitor.visit(alphaElement, context);
firstVisitor.visit(betaElement, context);
```
