# Git variables
GIT_COMMIT ?= $(shell git log -1 --pretty=format:"%H")
GIT_COMMIT_SHORT ?= $(shell git log -1 --pretty=format:"%h")
GIT_BRANCH ?= $(shell git branch | grep '*' | sed 's/* //')
SAFE_GIT_BRANCH ?= $(shell echo $(GIT_BRANCH) | sed 's@/@-@' | tr -s -c '\n\-\+a-z0-9' | tr '[:upper:]' '[:lower:]')
DOMAIN ?= football-app.myapp
#Docker variables
NAME = football
VERSION = 1.0.0

# Maven
maven_build:
	mvn clean install
.PHONY: maven_build

build:
	docker build -t $(NAME):$(VERSION) --build-arg SPRING_PROFILE=$(PROFILE) . --no-cache
