package com.microservices.notificationservice.emailService;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    final private JavaMailSender javaMailSender;
    final private Cloudinary cloudinary;

    public void sendEmailWithAttachment(String email, String cloudinaryPublicId) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            log.info("trying to email");
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSentDate(new java.util.Date());
            helper.setFrom("eatTooMuch.com");
            helper.setSubject("Your meal");

            // Download the file from Cloudinary
            File downloadedFile = downloadFileFromCloudinary(cloudinaryPublicId);

            // Attach the downloaded file to the email
            FileSystemResource file = new FileSystemResource(downloadedFile);
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            helper.setText("", true);
            javaMailSender.send(mimeMessage);

            // Clean up the downloaded file after sending the email
            downloadedFile.delete();
        } catch (MessagingException e) {
            log.error("exception in messaging sending :{}", e.getMessage());
            throw new RuntimeException("Failed to send email with attachment.", e);
        } catch (Exception e) {
            log.error("exception in email service :{}", e.getMessage());
            throw new RuntimeException("Unexpected error while sending email.", e);
        }
    }

    private File downloadFileFromCloudinary(String cloudinaryPublicId) throws IOException {
        try {
            String cloudinaryUrl = cloudinary.url().format("pdf").generate(cloudinaryPublicId); // Generate Cloudinary URL with desired format

            URL url = new URL(cloudinaryUrl);
            InputStream in = url.openStream(); // Open InputStream from Cloudinary URL

            File downloadedFile = File.createTempFile("downloadedFile", ".pdf"); // Create a temporary file
            FileOutputStream out = new FileOutputStream(downloadedFile);

            // Write the InputStream to the temporary file
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();
            out.close();

            return downloadedFile;
        } catch (MalformedURLException e) {
            throw new IOException("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error downloading file from Cloudinary: " + e.getMessage());
        }
    }

}
