package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.service.CrewService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CrewServiceProxy implements InvocationHandler {
    private CrewService obj;

    private CrewServiceProxy(CrewService obj) {
        this.obj = obj;
    }

    public Object invoke(
            Object proxy, Method m, Object[] args)
            throws Throwable {
        Object result;
        NassaContext context = (NassaContext) Main.getApplicationMenu().getApplicationContext();
        while(!context.getCanAccessCrewCache()) Thread.currentThread().wait(1000);
        context.setCanAccessCrewCache(false);
        result = m.invoke(obj, args);
        context.setCanAccessCrewCache(true);
        return result;
    }

    static CrewService newInstance(CrewService service) {
        return (CrewService) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                new Class[]{CrewService.class},
                new CrewServiceProxy(service)
        );
    }
}
