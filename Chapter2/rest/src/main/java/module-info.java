
module com.packtpub.mmj.rest {

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;

  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires com.packtpub.mmj.lib;

  exports com.packtpub.mmj.rest;
  exports com.packtpub.mmj.rest.resources;

  opens com.packtpub.mmj.rest;
  opens com.packtpub.mmj.rest.resources;
}
