package org.myddd.stater.bootstrap

import io.vertx.core.Vertx
import org.myddd.vertx.api.DocumentApplication
import org.myddd.vertx.application.DocumentApplicationImpl
import org.myddd.vertx.domain.DocumentRepository
import org.myddd.vertx.infra.DocumentRepositoryHibernate
import org.myddd.vertx.string.RandomIDString
import org.myddd.vertx.string.RandomIDStringProvider

class GuiceModule(vertx: Vertx):AbstractWebModule(vertx = vertx) {

    override fun configure() {
        super.configure()

        bind(DocumentRepository::class.java).to(DocumentRepositoryHibernate::class.java)
        bind(DocumentApplication::class.java).to(DocumentApplicationImpl::class.java)
    }
}