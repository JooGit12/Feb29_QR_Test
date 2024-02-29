package com.service.qr.feb29_qr_test.Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class QrController {

    /**
     * HTTP GET 요청에 대한 QR 코드 생성 핸들러
     * @param url QR 코드로 포함될 URL
     * @return ResponseEntity: HTTP 응답 객체
     */
    @GetMapping("/qr")
    public Object createQr(@RequestParam String url) throws WriterException, IOException {
        // QR 코드의 폭과 높이 설정
        int width = 200;
        int height = 200;

        // URL을 이용하여 QR 코드 생성
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            // BitMatrix를 PNG 이미지로 변환하고 ByteArrayOutputStream에 저장
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);

            // HTTP 응답 객체 생성
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)  // 응답의 컨텐츠 타입을 이미지 PNG로 설정
                    .body(out.toByteArray());  // 변환된 이미지의 바이트 배열을 응답 본문으로 설정
        }
    }
}
