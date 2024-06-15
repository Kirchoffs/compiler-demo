#!/bin/bash

MAIN_DIR="src/main/java"
TEST_DIR="src/test/java"
CLASSES_DIR="target/classes"
TEST_CLASSES_DIR="target/test-classes"

mkdir -p ${CLASSES_DIR}
mkdir -p ${TEST_CLASSES_DIR}

# Step 1: Compile the source code
echo "Compiling source code..."
javac -d ${CLASSES_DIR} -sourcepath ${MAIN_DIR} \
    ${MAIN_DIR}/org/syh/demo/regexp/State.java \
    ${MAIN_DIR}/org/syh/demo/regexp/FA.java \
    ${MAIN_DIR}/org/syh/demo/regexp/nfa/Constants.java \
    ${MAIN_DIR}/org/syh/demo/regexp/nfa/NFA.java \
    ${MAIN_DIR}/org/syh/demo/regexp/dfa/DFA.java \

if [ $? -ne 0 ]; then
    echo "Compilation of source code failed, exiting."
    exit 1
fi

# Step 2: Compile the test code
echo "Compiling test code..."
javac -d ${TEST_CLASSES_DIR} -cp ${CLASSES_DIR} -sourcepath ${TEST_DIR} \
    ${TEST_DIR}/org/syh/demo/regexp/nfa/NFATest.java \
    ${TEST_DIR}/org/syh/demo/regexp/dfa/DFATest.java \

if [ $? -ne 0 ]; then
    echo "Compilation of test code failed, exiting."
    exit 1
fi

# Step 3: Run the test class
echo "Running tests..."
java -ea -cp ${CLASSES_DIR}:${TEST_CLASSES_DIR} org.syh.demo.regexp.nfa.NFATest && \
java -ea -cp ${CLASSES_DIR}:${TEST_CLASSES_DIR} org.syh.demo.regexp.dfa.DFATest

# Check if the tests ran successfully
if [ $? -ne 0 ]; then
    echo "Tests failed."
else
    echo "Tests completed successfully."
fi
