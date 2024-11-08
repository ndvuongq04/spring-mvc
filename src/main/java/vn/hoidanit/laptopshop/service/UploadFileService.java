package vn.hoidanit.laptopshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadFileService {

    private final ServletContext servletContext;

    public UploadFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        // tên file
        String finalName = "";
        try {
            // chuyển file thành các bit 0-1
            byte[] bytes = file.getBytes();
            // this.servletContext.getRealPath("") -> chuyển đến thư mục /webapp
            // chuyển đến thư mục : /webapp/resources/images
            String rootPath = this.servletContext.getRealPath("/resources/images");
            // File.separator là : "/"
            // Tạo ra 1 đối tượng file cho thư mục : /webapp/resources/images/avatar
            File dir = new File(rootPath + File.separator + "avatar");
            // dir đã tồn tại trên hệ thống chưa -> chưa thì tạo mới file này
            if (!dir.exists())
                dir.mkdirs();

            // lưu tên file
            // System.currentTimeMillis() : thời gian ms
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            // lưu file
            stream.write(bytes);
            // đóng stream
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return finalName;
    }
}
