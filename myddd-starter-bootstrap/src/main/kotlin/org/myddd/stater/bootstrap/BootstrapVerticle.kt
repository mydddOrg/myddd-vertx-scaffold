package org.myddd.stater.bootstrap

import com.google.inject.AbstractModule
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import org.myddd.stater.bootstrap.route.DocumentRoute
import org.myddd.vertx.web.router.BootstrapVerticle

class BootstrapVerticle (port:Int = 8080) : BootstrapVerticle(port = port){
    override fun abstractModules(vertx: Vertx): AbstractModule {
        return GuiceModule(vertx)
    }

    override fun routers(vertx: Vertx, router: Router): () -> Unit {
        return {
            DocumentRoute(vertx,router)
        }
    }
}