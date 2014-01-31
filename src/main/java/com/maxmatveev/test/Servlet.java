package com.maxmatveev.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

/**
 * User: "Max Matveev"<mail@maxmatveev.com>
 * Date: 30.01.14
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class Servlet extends HttpServlet {
    private static byte[] bytes;

    static {
        long timer = System.currentTimeMillis();
        Random r = new Random(0);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); GZIPOutputStream zipStream = new GZIPOutputStream(baos)) {
            for (int i = 0; i < 1048576 * 50; i++) {
                zipStream.write(r.nextInt());
                if (i % 1048576 == 0) {
                    System.out.println("Generated " + (i / 1048576) + "MB so far");
                }
            }
            zipStream.finish();
            baos.flush();
            bytes = baos.toByteArray();
            System.out.println("generated " + bytes.length + " bytes of nearly uncompressable data in " + (System.currentTimeMillis() - timer) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().write(bytes);
    }
}
