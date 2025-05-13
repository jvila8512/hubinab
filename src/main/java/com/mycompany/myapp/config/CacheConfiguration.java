package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.TipoIdea.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoIdea.class.getName() + ".ideas");
            createCache(cm, com.mycompany.myapp.domain.TipoIdea.class.getName() + ".innovacionRacionalizacions");
            createCache(cm, com.mycompany.myapp.domain.Idea.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Reto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Reto.class.getName() + ".ideas");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".usurioecosistemas");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".retos");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".componentes");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".ecosistemaPeticiones");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".innovacionRacionalizacions");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".noticias");
            createCache(cm, com.mycompany.myapp.domain.UsuarioEcosistema.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Componentes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EcosistemaRol.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EcosistemaRol.class.getName() + ".ecosistemas");
            createCache(cm, com.mycompany.myapp.domain.EcosistemaPeticiones.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Proyectos.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Proyectos.class.getName() + ".partipantes");
            createCache(cm, com.mycompany.myapp.domain.Participantes.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Participantes.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.InnovacionRacionalizacion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Tramite.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Noticias.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoNoticia.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoNoticia.class.getName() + ".noticias");
            createCache(cm, com.mycompany.myapp.domain.Contacto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoContacto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Componentes.class.getName() + ".ecosistemas");
            createCache(cm, com.mycompany.myapp.domain.Idea.class.getName() + ".comentarios");
            createCache(cm, com.mycompany.myapp.domain.Idea.class.getName() + ".ideas");
            createCache(cm, com.mycompany.myapp.domain.UsuarioEcosistema.class.getName() + ".ecosistemas");
            createCache(cm, com.mycompany.myapp.domain.Proyectos.class.getName() + ".sectors");
            createCache(cm, com.mycompany.myapp.domain.Proyectos.class.getName() + ".lineaInvestigacions");
            createCache(cm, com.mycompany.myapp.domain.Proyectos.class.getName() + ".ods");
            createCache(cm, com.mycompany.myapp.domain.ComenetariosIdea.class.getName());
            createCache(cm, com.mycompany.myapp.domain.LikeIdea.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Entidad.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Entidad.class.getName() + ".ideas");
            createCache(cm, com.mycompany.myapp.domain.TipoProyecto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TipoProyecto.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.Sector.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Sector.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.LineaInvestigacion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.LineaInvestigacion.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.Ods.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Ods.class.getName() + ".proyectos");
            createCache(cm, com.mycompany.myapp.domain.Idea.class.getName() + ".likes");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".redesUrls");
            createCache(cm, com.mycompany.myapp.domain.RedesSociales.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CategoriaUser.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CategoriaUser.class.getName() + ".usurioecosistemas");
            createCache(cm, com.mycompany.myapp.domain.Servicios.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactoServicio.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ChangeMacker.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ContactoChangeMacker.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".aniristas");
            createCache(cm, com.mycompany.myapp.domain.Anirista.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".ecosistemaComponentes");
            createCache(cm, com.mycompany.myapp.domain.Ecosistema.class.getName() + ".users");
            createCache(cm, com.mycompany.myapp.domain.EcosistemaComponente.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
