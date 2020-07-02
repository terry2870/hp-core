/**
 * 
 */
package com.hp.core.test.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hp.core.test.es.model.response.ProjectResponse;

/**
 * @author huangping
 * Jun 22, 2020
 */
public interface IProjectRepository extends ElasticsearchRepository<ProjectResponse, Integer> {

}
