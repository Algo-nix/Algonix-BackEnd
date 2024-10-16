# Python 기반 이미지 사용
FROM python:3.8-slim

# 필수 패키지 설치
RUN apt-get update && apt-get install -y time

# 코드 복사 및 실행
COPY main.py /main.py
ENTRYPOINT ["/usr/bin/time", "-v", "python3", "/main.py"]
