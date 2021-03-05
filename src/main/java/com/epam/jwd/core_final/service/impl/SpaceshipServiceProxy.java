package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SpaceshipServiceProxy implements InvocationHandler {
    private SpaceshipService service;

    private SpaceshipServiceProxy(SpaceshipService service) {
        this.service = service;
    }

    public Object invoke(
            Object proxy, Method m, Object[] args)
            throws Throwable {
        Object result;
        NassaContext context = (NassaContext) Main.getApplicationMenu().getApplicationContext();
        while(!context.getCanAccessShipsCache()) Thread.currentThread().wait(1000);
        context.setCanAccessShipsCache(false);
        result = m.invoke(service, args);
        context.setCanAccessShipsCache(true);
        return result;
    }

    static SpaceshipService newInstance(SpaceshipService service) {
        return (SpaceshipService) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                new Class[]{SpaceshipService.class},
                new SpaceshipServiceProxy(service)
        );
    }
}
