JAVAC=javac
JAR=jar
JARFLAGS=-c -e Toast

all: toast.jar

Toast.class: toast.java
	$(JAVAC) $^

toast.jar: Toast.class
	$(JAR) $(JARFLAGS) -f $@ Toast*.class

clean:
	rm -f *.jar *.class
