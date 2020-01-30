package service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CaptchaUtil {

    // Captcha를 생성하는 유틸리티 클래스

    public static void getCaptcha(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // HTTP 캐시 제어
        // 기존에 만들어진 데이터(가 있다면)를 없애버리고 새로운 환경에서 시작
        res.setHeader("Cache-Control", "no-cache");
        res.setHeader("Progma", "no-cache");
        res.setDateHeader("Expires", 0); // 시간 지정시 30분은 유지, 0으로 만든 이유는 언제나 서버에 새로운 데이터 요청하기 위함
        res.setDateHeader("Max-Age", 0);

        // 세션 제어
        HttpSession sess = req.getSession();
        // 이전에 만든 captcha가 존재하는 경우 세션에 저장된 captcha 문자를 알아냄
        String ch = (String) sess.getAttribute("cta");

        // captcha 이미지 생성순서
        // 이미지에 출력할 난수 생성
        // 난수를 나타낼 이미지 생성
        // 응답객체로 이미지 출력

        // 난수생성 : 세션이 없는 경우에만 만듦
        if(ch == null) {
            Random r = new Random();
            String token = Long.toString(r.nextLong(), 36); // 36개의 임의의 난수 생성하여
            ch = token.substring(1, 8); // 첫번째열의 난수에서 6번째까지 잘라서 가져옴
            System.out.println(token + "/" + ch); // 확인용
        }

        // 이미지 생성
        // captcha를 출력할 이미지 영역 정의 (가로/세로/종류)
        BufferedImage img = new BufferedImage(300, 80, BufferedImage.TYPE_INT_RGB);

        // 메모리에 이미지 영역 생성
        Graphics2D g2d = img.createGraphics();

        // 색상지정
        Color c = new Color(0.6663f, 0.4659f, 0.3232f);

        // 문자에 적용할 그라데이션 정의
        GradientPaint gp = new GradientPaint(30, 30, Color.RED, 15,25, Color.WHITE, true); // 30,30은 위치 / 15,25는 방향

        // 이미지 영역에 색깔 입히기
        g2d.setPaint(gp);

        // 글자체 및 크기 지정
        Font f = new Font("verdana", Font.CENTER_BASELINE, 55); // 글자가 가운데 오도록 설정

        // 이미지 영역에 글자체 지정
        g2d.setFont(f);

        // 난수형태의 문자를 이미지 영역에 그림
        g2d.drawString(ch, 10, 60); // x축 10 y축 60 위치에 이미지 생성

        // 작업 종료
        g2d.dispose();

        // 응답객체로 이미지 출력
        // 바이너리위주 출력을 위해 응답객체 생성
        OutputStream os = res.getOutputStream();

        // 메모리영역에 생성된 이미지 객체를 이미지 형식으로 os 변수가 가리키는 곳으로 쏨(보냄)
        ImageIO.write(img, "png", os); // 이미지를 png 형식으로 보냄(os)
        os.flush();
        os.close();

        // 생성된 난수는 세션변수에 저장
        sess.setAttribute("cta", ch);

    }
}
