This makes an Android-like toast.
Based on code sample from here: https://stackoverflow.com/a/32817660/4215359

Build (in case you can't just `make`):
javac toast.java
jar -c -e Toast -f toast.jar *.class

Run:
toast.jar \<text> \<font size> \<display time in seconds (float)> \<x (int)> \<y (int)>

Enjoy :)