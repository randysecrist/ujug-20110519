all: build

build:
	mvn package

clean:
	mvn clean
	find . -name "MANIFEST*" -exec rm {} \;

distclean: clean
	find . -regex '.*\(~\|#\|\.swp\)' -exec rm {} \;
	rm -rf deploy

get-virgo:
	install -d -m0755 deploy
	wget -P deploy -N http://mirrors.xmission.com/eclipse/virgo/release/VWS/2.1.1.RELEASE/virgo-web-server-2.1.1.RELEASE.zip

configure-virgo: get-virgo
	unzip -o -d deploy deploy/virgo-web-server-2.1.1.RELEASE.zip

deploy: build get-virgo configure-virgo
	cp -pr dictionary-api/target/dictionary-api*.jar dictionary-english/target/dictionary-english*.jar dictionary-korean/target/dictionary-korean*.jar dictionary-war/target/dictionary-war*.war deploy/virgo-web-server-2.1.1.RELEASE/pickup/
