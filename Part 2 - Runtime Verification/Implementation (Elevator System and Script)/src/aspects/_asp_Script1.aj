package aspects;

import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import larva.*;
public aspect _asp_Script1 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_Script1.initialize();
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("time1Clock"))) && (if (millis == 3000)) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 20/*clock1*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 20/*clock1*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("time2Clock"))) && (if (millis == 3000)) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 22/*clock2*/);
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 22/*clock2*/);
}
}
}
}
before ( LiftController __2,Lift l1) : (call(* LiftController.moveLift(..)) && target(__2) && args(l1,*) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =l1 ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.__2 = __2;
_cls_inst.l1 = l1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*requestOrSummon*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*requestOrSummon*/);
}
}
before ( Lift l1,boolean isNowMoving) : (call(* Lift.setMoving(..)) && target(l1) && args(isNowMoving) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =l1 ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.l1 = l1;
_cls_inst.isNowMoving = isNowMoving;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*startOrStopMoving*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*startOrStopMoving*/);
}
}
before ( Lift l1) : (call(* Lift.openDoors(..)) && target(l1) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =l1 ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.l1 = l1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*openDoors*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*openDoors*/);
}
}
before ( LiftController ctrl,int liftNo) : (call(* LiftController.moveLift(..)) && target(ctrl) && args(liftNo,*) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =ctrl .getLifts ()[liftNo] ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.ctrl = ctrl;
_cls_inst.liftNo = liftNo;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*floorRequest*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 18/*floorRequest*/);
}
}
before ( Lift l1) : (call(* Lift.startClosingDoors(..)) && target(l1) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =l1 ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.l1 = l1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*startClosingDoors*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 16/*startClosingDoors*/);
}
}
before ( Lift l1) : (call(* Lift.closeDoors(..)) && target(l1) && !cflow(adviceexecution())) {

synchronized(_asp_Script0.lock){
Lift l;
l =l1 ;

_cls_Script1 _cls_inst = _cls_Script1._get_cls_Script1_inst( l);
_cls_inst.l1 = l1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*closeDoors*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*closeDoors*/);
}
}
}