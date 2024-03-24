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
