./compile.sh
docker login -u remiti -p 'H&bb6p&)YjXwi]*'
docker build -t remiti/securewin_manager:latest .
docker tag remiti/securewin_manager:latest remiti/securewin_manager
docker push remiti/securewin_manager
docker logout
#docker rmi remiti/securewin_manager