package com.hanchiang.creating.destroying;

import java.io.*;

/**
 * Prefer try-with-resources to try-finally.
 * The Java libraries include many resources that must be closed manually by invoking a close method.
 * Examples include InputStream, OutputStream, and java.sql.Connection.
 * Closing resources is often overlooked by clients, with predictably dire performance consequences.
 *
 * Why is try-finally not recommended?
 * Because statements in both try and finally are equally capable of throwing exceptions. The exception
 * from the try block will be suppressed, which is not what we want.
 * This presents additionally complexity when it comes to freeing resources.
 * With nested try-finally blocks, it becomes even more difficult to ensure resources are released correctly.
 *
 * try-with-resources solves all these problems!
 * A resource needs to implement the AutoCloseable interface, which consists of a close method.
 */
public class TryWithResources {
  // try-with-resources - the best way to close resources!
  static String firstLineOfFile(String path) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      return br.readLine();
    }
  }

  // try-with-resources on multiple resources - short and sweet
  static void copy(String src, String dst) throws IOException {
    try (InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst)) {
      int BUFFER_SIZE = 100;
      byte[] buf = new byte[BUFFER_SIZE];
      int n;
      while ((n = in.read(buf)) >= 0) {
        out.write(buf, 0, n);
      }
    }
  }

  // try-with-resources with a catch clause
  static String firstLineOfFile(String path, String defaultVal) {
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      return br.readLine();
    } catch (IOException e) {
      return defaultVal;
    }
  }
}
