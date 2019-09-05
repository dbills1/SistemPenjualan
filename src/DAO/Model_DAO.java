package DAO;

import java.util.List;

public interface Model_DAO <A> {
    public int autonumber(A Object);
    public void insert(A Object);
    public void update(A Object);
    public void delete(Integer id);
    public List<A> getAll();
    public List<A> getcari(String key);
}
