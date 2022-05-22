package gay.lemmaeof.aleph.one;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}
