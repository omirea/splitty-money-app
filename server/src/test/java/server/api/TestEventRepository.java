package server.api;

import commons.Event;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.EventRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class TestEventRepository implements EventRepository {

    /**
     *
     */
    @Override
    public void flush() {

    }

    /**
     * @param entity entity to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> S saveAndFlush(S entity) {
        return null;
    }

    /**
     * @param entities entities to be saved. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    /**
     * @param entities entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllInBatch(Iterable<Event> entities) {

    }

    /**
     * @param strings the ids of the entities to be deleted. Must not be {@literal null}.
     */
    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    /**
     *
     */
    @Override
    public void deleteAllInBatch() {

    }

    /**
     * @param string must not be {@literal null}.
     * @return
     */
    @Override
    public Event getOne(String string) {
        return null;
    }

    /**
     * @param string must not be {@literal null}.
     * @return
     */
    @Override
    public Event getById(String string) {
        return null;
    }

    /**
     * @param string must not be {@literal null}.
     * @return
     */
    @Override
    public Event getReferenceById(String string) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    /**
     * @param example must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> List<S> findAll(Example<S> example) {
        return null;
    }

    /**
     * @param example must not be {@literal null}.
     * @param sort    the {@link Sort} specification to sort the results by, may be
     * {@link Sort#unsorted()}, must not be
     *                {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    /**
     * @param example  must not be {@literal null}.
     * @param pageable the pageable to request a paged result, can be
     * {@link Pageable#unpaged()}, must not be
     *                 {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    /**
     * @param example the {@link Example} to count instances for. Must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> long count(Example<S> example) {
        return 0;
    }

    /**
     * @param example the {@link Example} to use for the existence check. Must not be
     * {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> boolean exists(Example<S> example) {
        return false;
    }

    /**
     * @param example       must not be {@literal null}.
     * @param queryFunction the query function defining projection, sorting, and the result type
     * @param <S>
     * @param <R>
     * @return
     */
    @Override
    public <S extends Event, R> R findBy(Example<S> example,
                                         Function<FluentQuery.FetchableFluentQuery<S>,
                                             R> queryFunction) {
        return null;
    }

    /**
     * @param entity must not be {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> S save(S entity) {
        return null;
    }

    /**
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @param <S>
     * @return
     */
    @Override
    public <S extends Event> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    /**
     * @param string must not be {@literal null}.
     * @return
     */
    @Override
    public Optional<Event> findById(String string) {
        return Optional.empty();
    }

    /**
     * @param string must not be {@literal null}.
     * @return
     */
    @Override
    public boolean existsById(String string) {
        return false;
    }

    /**
     * @return
     */
    @Override
    public List<Event> findAll() {
        return null;
    }

    /**
     * @param strings must not be {@literal null} nor contain any {@literal null} values.
     * @return
     */
    @Override
    public List<Event> findAllById(Iterable<String> strings) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * @param string must not be {@literal null}.
     */
    @Override
    public void deleteById(String string) {

    }

    /**
     * @param entity must not be {@literal null}.
     */
    @Override
    public void delete(Event entity) {

    }

    /**
     * @param strings must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    /**
     * @param entities must not be {@literal null}. Must not contain {@literal null} elements.
     */
    @Override
    public void deleteAll(Iterable<? extends Event> entities) {

    }

    /**
     *
     */
    @Override
    public void deleteAll() {

    }

    /**
     * @param sort the {@link Sort} specification to sort the results by, can be {
     * @link Sort#unsorted()}, must not be
     *             {@literal null}.
     * @return
     */
    @Override
    public List<Event> findAll(Sort sort) {
        return null;
    }

    /**
     * @param pageable the pageable to request a paged result, can be
     * {@link Pageable#unpaged()}, must not be
     *                 {@literal null}.
     * @return
     */
    @Override
    public Page<Event> findAll(Pageable pageable) {
        return null;
    }
}
