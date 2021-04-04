open module neon.units.server {
    requires neon.units.in_memory;
    requires neon.plugins.tags;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires spring.boot.starter.tomcat;
}
