package ga.tianyuge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GhostscriptRepair {

    public static void main(String[] args) {
        try {
            // Ghostscript 命令
            String[] command = {
                    "C:\\Program Files\\gs\\gs10.05.0\\bin\\gswin64.exe",
                    "-o", "C:\\Users\\sa\\Documents\\reprint_gs.pdf",
                    "-sDEVICE=pdfwrite",
                    "-dPDFSETTINGS=/prepress", // 或 /screen, /ebook, /printer
                    "C:\\Users\\sa\\Documents\\origin.pdf"
            };

            // 执行 Ghostscript 命令
            Process process = Runtime.getRuntime().exec(command);

            // 读取 Ghostscript 输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待 Ghostscript 执行完成
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("PDF 文档已修复并保存为 output_gs.pdf");
            } else {
                System.err.println("Ghostscript 执行失败，退出码：" + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}