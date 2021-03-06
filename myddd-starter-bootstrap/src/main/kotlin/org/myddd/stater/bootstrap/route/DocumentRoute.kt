package org.myddd.stater.bootstrap.route


import cc.lingenliu.example.document.bootstrap.validation.DocumentValidationHandler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.myddd.stater.bootstrap.WebErrorCode
import org.myddd.vertx.api.DocumentApplication
import org.myddd.vertx.api.DocumentDTO
import org.myddd.vertx.base.BusinessLogicException
import org.myddd.vertx.ioc.InstanceFactory
import org.myddd.vertx.web.router.AbstractRouter
import org.myddd.vertx.web.router.ext.jsonFormatEnd
import java.util.*

class DocumentRoute(vertx: Vertx, router: Router): AbstractRouter(vertx = vertx,router = router)  {

    init {
        createDocumentRoute()
        queryDocumentRoute()
    }

    private val documentApplication by lazy { InstanceFactory.getInstance(DocumentApplication::class.java) }

    private fun createDocumentRoute(){

        createPostRoute(path = "/$version/documents"){ route ->

            route.handler(DocumentValidationHandler().createDocumentValidationHandler())

            route.handler {
                GlobalScope.launch(vertx.dispatcher()) {
                    try {
                        val body = it.bodyAsJson
                        val documentDTO = body.mapTo(DocumentDTO::class.java)
                        val created = documentApplication.createDocument(documentDTO).await()
                        it.jsonFormatEnd(JsonObject.mapFrom(created).toBuffer())
                    }catch (t:Throwable){
                        it.fail(t)
                    }
                }
            }


        }
    }

    private fun queryDocumentRoute(){

        createGetRoute(path = "/$version/documents/:id"){ route ->
            route.handler {
                GlobalScope.launch(vertx.dispatcher()) {
                    try {

                        val id = it.pathParam("id").toLong()
                        val queryDocument = documentApplication.queryDocumentById(id).await()
                        if(Objects.isNull(queryDocument))throw BusinessLogicException(WebErrorCode.MEDIA_NOT_FOUND)

                        it.jsonFormatEnd(JsonObject.mapFrom(queryDocument!!).toBuffer())
                    }catch (t:Throwable){
                        it.fail(t)
                    }
                }
            }
        }

    }
}