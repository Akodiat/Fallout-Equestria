package utils;
import org.lwjgl.input.Keyboard;

public enum Keys {
	K_0(Keyboard.KEY_0),
	K_1(Keyboard.KEY_1),
	K_2(Keyboard.KEY_2),
	K_3(Keyboard.KEY_3),
	K_4(Keyboard.KEY_4),
	K_5(Keyboard.KEY_5),
	K_6(Keyboard.KEY_6),
	K_7(Keyboard.KEY_7),
	K_8(Keyboard.KEY_8),
	K_9(Keyboard.KEY_9),
	A(Keyboard.KEY_A),
	B(Keyboard.KEY_B),
	C(Keyboard.KEY_C),
	D(Keyboard.KEY_D),
	E(Keyboard.KEY_E),
	F(Keyboard.KEY_F),
	G(Keyboard.KEY_G),
	H(Keyboard.KEY_H),
	I(Keyboard.KEY_I),
	J(Keyboard.KEY_J),
	K(Keyboard.KEY_K),
	L(Keyboard.KEY_L),
	M(Keyboard.KEY_M),
	N(Keyboard.KEY_N),
	O(Keyboard.KEY_O),
	P(Keyboard.KEY_P),
	Q(Keyboard.KEY_Q),
	R(Keyboard.KEY_R),
	S(Keyboard.KEY_S),
	T(Keyboard.KEY_T),
	U(Keyboard.KEY_U),
	V(Keyboard.KEY_V),
	W(Keyboard.KEY_W),
	X(Keyboard.KEY_X),
	Y(Keyboard.KEY_Y),
	Z(Keyboard.KEY_Z),
	Delete(Keyboard.KEY_DELETE),
	Escape(Keyboard.KEY_ESCAPE),
	Space(Keyboard.KEY_SPACE),
	Enter(Keyboard.KEY_RETURN),
	Tab(Keyboard.KEY_TAB),
	Up(Keyboard.KEY_UP),
	Left(Keyboard.KEY_LEFT),
	Right(Keyboard.KEY_RIGHT),
	Down(Keyboard.KEY_DOWN),
	Back(Keyboard.KEY_BACK),
	F1(Keyboard.KEY_F1),
	F2(Keyboard.KEY_F2),
	F3(Keyboard.KEY_F3),
	F4(Keyboard.KEY_F4),
	F5(Keyboard.KEY_F5),
	F6(Keyboard.KEY_F6),
	F7(Keyboard.KEY_F7),
	F8(Keyboard.KEY_F8),
	F9(Keyboard.KEY_F9),
	F10(Keyboard.KEY_F10),
	F11(Keyboard.KEY_F11),
	F12(Keyboard.KEY_F12), 
	LeftShift(Keyboard.KEY_LSHIFT),
	RightShift(Keyboard.KEY_RSHIFT);
	
	private int lwjglkeyCode;
	
	protected int getLwjglKeyCode() {
		return this.lwjglkeyCode;
	}
	
	Keys(int lwjglkeyCode) {
		this.lwjglkeyCode = lwjglkeyCode;
	}
	
	
	
}
