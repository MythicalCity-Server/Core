package zsantana.misc;

public class Container<T, R> {

	private final T _ITEM_1;
	private final R _ITEM_2;

	public Container(T item1, R item2) {
		this._ITEM_1 = item1;
		this._ITEM_2 = item2;
	}

	public T getItem1() {
		return this._ITEM_1;
	}

	public R getItem2() {
		return this._ITEM_2;
	}
}
