# Makefile para laboratorio 3 - Spark FeedReader

JAVAC=javac
JAVA=java
LIB_DIR=lib
OUT_DIR=out
SPARK_FOLDER=/mnt/c/spark-3.5.1-bin-hadoop3
SOURCES=$(shell find src -name "*.java")
MAIN_CLASS=FeedReaderMain

SPARK_JARS=$(shell find $(SPARK_FOLDER)/jars -name "*.jar" | tr '\n' ':')
CLASSPATH=$(OUT_DIR):$(LIB_DIR)/json-20240303.jar:$(SPARK_JARS)

all: build run

build:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -cp "$(OUT_DIR):$(LIB_DIR)/json-20240303.jar:$(SPARK_FOLDER)/jars/*" -d $(OUT_DIR) $(SOURCES)

run:
	$(JAVA) -cp "$(CLASSPATH)" $(MAIN_CLASS) -ne

run-articles:
	$(JAVA) -cp "$(CLASSPATH)" $(MAIN_CLASS)

clean:
	rm -rf $(OUT_DIR)

.PHONY: all build run run-articles clean
