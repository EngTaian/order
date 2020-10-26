package br.com.taian.order.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CrudServiceImpl<L extends JpaRepository, T extends BaseModel> implements CrudService<T> {

    @Autowired
    protected L repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<T> findAllById(List<Long> IDS) {
        return repository.findAllById(IDS);
    }

    @Override
    public T findById(Long ID) {
        Optional<T> optional = repository.findById(ID);
        return optional.orElse(null);
    }

    @Override
    public T findById(String ID) {
        Optional<T> optional = repository.findById(ID);
        return optional.orElse(null);
    }

    @Override
    public T findById(Object ID) {
        Optional<T> optional = repository.findById(ID);
        return optional.orElse(null);
    }

    public T getOne(Long ID){
        return (T) repository.getOne(ID);
    }

    public T getOne(Object ID){
        return (T) repository.getOne(ID);
    }

    @Override
    public T createElement(T o) {
        o.setId(null);
        return (T) repository.save(o);
    }

    @Override
    @Transactional
    public List<T> createElements(List o) {
        return (List<T>) repository.saveAll(o);
    }

    @Override
    @Transactional
    public T updateElement(Long k, T o) {
        Optional<T> t = (Optional) repository.findById(k);
        if (t.isPresent()){
            o.setId(t.get().getId());
            o.setVersion(t.get().getVersion());
            return (T) repository.save(o);
        }
        return null;
    }

    @Override
    public boolean deleteElement(Long ID) {
        boolean exists = repository.existsById(ID);
        if (exists){
            repository.deleteById(ID);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteElement(T element) {
        boolean exists = repository.existsById(element.getId());
        if (exists){
            repository.delete(element);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public long count(){
        return repository.count();
    }


}
