package com.hamidur.np.dao.repos;

import com.hamidur.np.dao.models.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>
{
    // returns all Articles
    Set<Article> findAll();

    Set<Article> getArticlesByBodyContains(String text);

    // returns all Articles written by an author associated with this id
    @Query(nativeQuery = true,
            value = "select * from articles a inner join authors_articles aa " +
                    "on a.article_id = aa.article_id and aa.user_id = (:author_id)")
    Set<Article> getArticlesByAuthorId(@Param("author_id") Integer authorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from authors_articles aa where aa.article_id = (:articleId);" +
                    "delete from comments c where c.article_id = (:articleId);" +
                    "delete from articles a where a.article_id = (:articleId) and is_published = true;")
    int deletePublishedArticleById(@Param("articleId") Integer articleId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from authors_articles aa where aa.article_id = (:articleId);" +
                    "delete from articles a where a.article_id = (:articleId) and is_published = false;")
    int deleteUnpublishedArticleById(@Param("articleId") Integer articleId);

    @Query(nativeQuery = true, value = "select * from articles where article_id not in (select article_id from authors_articles) and is_published = true;")
    Set<Article> getPublishedArticlesWithNoAuthor();

    // Not for user or author
    @Query(nativeQuery = true, value = "select * from articles where article_id not in (select article_id from authors_articles) and is_published = false;")
    Set<Article> getUnPublishedArticlesWithNoAuthor();
}
