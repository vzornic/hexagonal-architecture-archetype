MVN=mvn
GIT=git

SONAR_HOST=http://localhost:9000
SONAR_LOGIN=admin
SONAR_PASSWORD=admin

PWD=$(shell pwd)
SONAR_CODE_COVERAGE_XML_REPORTS=$(PWD)/code-coverage/target/site/jacoco-aggregate/jacoco.xml

build:
	$(MVN) clean install -P local

sonar:
	$(MVN) clean verify sonar:sonar -Dsonar.host.url=$(SONAR_HOST) \
							   -Dsonar.login=$(SONAR_LOGIN) \
							   -Dsonar.password=$(SONAR_PASSWORD) \
							   -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco-report/jacoco.xml,target/jacoco-report-it/jacoco.xml,$(SONAR_CODE_COVERAGE_XML_REPORTS) \
							   -P local