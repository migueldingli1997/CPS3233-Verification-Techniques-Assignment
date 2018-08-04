package aspects;

import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import larva.*;
public aspect _asp_Script2 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Script2.initialize();
}
}
before ( int currFloor,Shaft s1) : (call(* Shaft.animateDown(..)) && target(s1) && args(currFloor) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Shaft s;
s =s1 ;

_cls_Script2 _cls_inst = _cls_Script2._get_cls_Script2_inst( s);
_cls_inst.currFloor = currFloor;
_cls_inst.s1 = s1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 26/*animateDn*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 26/*animateDn*/);
}
}
before ( int currFloor,Shaft s1) : (call(* Shaft.animateUp(..)) && target(s1) && args(currFloor) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Shaft s;
s =s1 ;

_cls_Script2 _cls_inst = _cls_Script2._get_cls_Script2_inst( s);
_cls_inst.currFloor = currFloor;
_cls_inst.s1 = s1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 24/*animateUp*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 24/*animateUp*/);
}
}
}