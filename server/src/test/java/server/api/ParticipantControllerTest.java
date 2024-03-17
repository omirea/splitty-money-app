package server.api;

import commons.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ParticipantRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipantControllerTest {

    @Test
    public void idTest(){
        long id=2010;
        ParticipantRepository db=new ParticipantRepository() {
            @Override
            public List<Participant> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<Participant> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Participant> S save(S entity) {
                return null;
            }

            @Override
            public <S extends Participant> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public Optional<Participant> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public List<Participant> findAll() {
                return null;
            }

            @Override
            public List<Participant> findAllById(Iterable<Long> longs) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {
            }

            @Override
            public void delete(Participant entity) {
            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {
            }

            @Override
            public void deleteAll(Iterable<? extends Participant> entities) {
            }

            @Override
            public void deleteAll() {
            }

            @Override
            public void flush() {
            }

            @Override
            public <S extends Participant> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Participant> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<Participant> entities) {
            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {
            }

            @Override
            public void deleteAllInBatch() {
            }

            @Override
            public Participant getOne(Long aLong) {
                return null;
            }

            @Override
            public Participant getById(Long aLong) {
                return null;
            }

            @Override
            public Participant getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Participant> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Participant> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Participant> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends Participant> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Participant> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Participant> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Participant, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
        String result="Hello 2010!";
        ParticipantController pc=new ParticipantController(db);
        assertEquals(pc.id(id), result);
    }
}
