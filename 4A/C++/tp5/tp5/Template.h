template<typename T>
void swapT(T &a, T&b) {
	T c = std::move(a);
	a = std::move(b);
	b = std::move(c);
}