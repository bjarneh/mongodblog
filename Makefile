#
# Include command line arguments here instead of config-file.
#
# 1. give it a fitting name
# 2. include servlet api in classpath
# 3. make sure we pack this jar file according to Apache's will
#
JZ_ARGS=-name mb.war\
		-classpath=$(CATALINA_HOME)/lib/servlet-api.jar:res/WEB-INF/lib/mongo-2.10.1.jar\
		-war



default: deploy


sanity:
ifndef CATALINA_HOME
	$(error CATALINA_HOME is undefined)
endif


deploy: build
	@cp dst/mb.war $(CATALINA_HOME)/webapps
	@echo cp war to: CATALINA_HOME/webapps

build: sanity
	@jz $(JZ_ARGS)

clean:
	@jz $(JZ_ARGS) -clean

tail:
	tail -f $(CATALINA_HOME)/logs/catalina.out




.PHONY: deploy build clean tail
