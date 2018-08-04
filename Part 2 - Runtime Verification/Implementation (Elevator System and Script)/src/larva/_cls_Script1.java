package larva;


import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_Script1 implements _callable{

public static LinkedHashMap<_cls_Script1,_cls_Script1> _cls_Script1_instances = new LinkedHashMap<_cls_Script1,_cls_Script1>();

_cls_Script0 parent;
public static LiftController __2;
public static Lift l1;
public static LiftController ctrl;
public static boolean isNowMoving;
public static int liftNo;
public Lift l;
int no_automata = 8;
 public int reqOrSumm =0 ;
public Clock time1Clock = new Clock(this,"time1Clock");
public Clock time2Clock = new Clock(this,"time2Clock");

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Script1( Lift l) {
parent = _cls_Script0._get_cls_Script0_inst();
time1Clock.register(3000l);
time2Clock.register(3000l);
this.l = l;
}

public void initialisation() {
   time1Clock.reset();
   time2Clock.reset();
}

public static _cls_Script1 _get_cls_Script1_inst( Lift l) { synchronized(_cls_Script1_instances){
_cls_Script1 _inst = new _cls_Script1( l);
if (_cls_Script1_instances.containsKey(_inst))
{
_cls_Script1 tmp = _cls_Script1_instances.get(_inst);
 return _cls_Script1_instances.get(_inst);
}
else
{
 _inst.initialisation();
 _cls_Script1_instances.put(_inst,_inst);
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Script1)
 && (l == null || l.equals(((_cls_Script1)o).l))
 && (parent == null || parent.equals(((_cls_Script1)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_Script1_instances){
_performLogic_invar1(_info, _event);
_performLogic_invar3(_info, _event);
_performLogic_invar4(_info, _event);
_performLogic_invar5(_info, _event);
_performLogic_temp4(_info, _event);
_performLogic_temp5(_info, _event);
_performLogic_time1(_info, _event);
_performLogic_time2(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_Script1[] a = new _cls_Script1[1];
synchronized(_cls_Script1_instances){
a = _cls_Script1_instances.keySet().toArray(a);}
for (_cls_Script1 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_Script1_instances){
_cls_Script1_instances.remove(this);}
synchronized(time1Clock){
time1Clock.off();
time1Clock._inst = null;
time1Clock = null;}
synchronized(time2Clock){
time2Clock.off();
time2Clock._inst = null;
time2Clock = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_invar1 = 6;

public void _performLogic_invar1(String _info, int... _event) {

_cls_Script0.pw.println("[invar1]AUTOMATON::> invar1("+l + " " + ") STATE::>"+ _string_invar1(_state_id_invar1, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_invar1==6){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (isNowMoving &&l .isOpen ())){
		
		_state_id_invar1 = 5;//moving to state bad
		_goto_invar1(_info);
		}
}
}

public void _goto_invar1(String _info){
_cls_Script0.pw.println("[invar1]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_invar1(_state_id_invar1, 1));
_cls_Script0.pw.flush();
}

public String _string_invar1(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 6: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_invar3 = 8;

public void _performLogic_invar3(String _info, int... _event) {

_cls_Script0.pw.println("[invar3]AUTOMATON::> invar3("+l + " " + ") STATE::>"+ _string_invar3(_state_id_invar3, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_invar3==8){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*requestOrSummon*/))){
		reqOrSumm ++;

		_state_id_invar3 = 8;//moving to state start
		_goto_invar3(_info);
		}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (isNowMoving &&reqOrSumm ==0 )){
		
		_state_id_invar3 = 7;//moving to state bad
		_goto_invar3(_info);
		}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (isNowMoving )){
		reqOrSumm --;

		_state_id_invar3 = 8;//moving to state start
		_goto_invar3(_info);
		}
}
}

public void _goto_invar3(String _info){
_cls_Script0.pw.println("[invar3]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_invar3(_state_id_invar3, 1));
_cls_Script0.pw.flush();
}

public String _string_invar3(int _state_id, int _mode){
switch(_state_id){
case 7: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 8: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_invar4 = 10;

public void _performLogic_invar4(String _info, int... _event) {

_cls_Script0.pw.println("[invar4]AUTOMATON::> invar4("+l + " " + ") STATE::>"+ _string_invar4(_state_id_invar4, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_invar4==10){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (!isNowMoving &&l .isBetweenFloors ())){
		
		_state_id_invar4 = 9;//moving to state bad
		_goto_invar4(_info);
		}
}
}

public void _goto_invar4(String _info){
_cls_Script0.pw.println("[invar4]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_invar4(_state_id_invar4, 1));
_cls_Script0.pw.flush();
}

public String _string_invar4(int _state_id, int _mode){
switch(_state_id){
case 9: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 10: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_invar5 = 12;

public void _performLogic_invar5(String _info, int... _event) {

_cls_Script0.pw.println("[invar5]AUTOMATON::> invar5("+l + " " + ") STATE::>"+ _string_invar5(_state_id_invar5, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_invar5==12){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/)) && (l .isBetweenFloors ())){
		
		_state_id_invar5 = 11;//moving to state bad
		_goto_invar5(_info);
		}
}
}

public void _goto_invar5(String _info){
_cls_Script0.pw.println("[invar5]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_invar5(_state_id_invar5, 1));
_cls_Script0.pw.flush();
}

public String _string_invar5(int _state_id, int _mode){
switch(_state_id){
case 11: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 12: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_temp4 = 15;

public void _performLogic_temp4(String _info, int... _event) {

_cls_Script0.pw.println("[temp4]AUTOMATON::> temp4("+l + " " + ") STATE::>"+ _string_temp4(_state_id_temp4, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_temp4==15){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		
		_state_id_temp4 = 14;//moving to state open
		_goto_temp4(_info);
		}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		
		_state_id_temp4 = 13;//moving to state bad
		_goto_temp4(_info);
		}
}
else if (_state_id_temp4==14){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		
		_state_id_temp4 = 15;//moving to state closed
		_goto_temp4(_info);
		}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		
		_state_id_temp4 = 13;//moving to state bad
		_goto_temp4(_info);
		}
}
}

public void _goto_temp4(String _info){
_cls_Script0.pw.println("[temp4]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_temp4(_state_id_temp4, 1));
_cls_Script0.pw.flush();
}

public String _string_temp4(int _state_id, int _mode){
switch(_state_id){
case 13: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 15: if (_mode == 0) return "closed"; else return "closed";
case 14: if (_mode == 0) return "open"; else return "open";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_temp5 = 20;

public void _performLogic_temp5(String _info, int... _event) {

_cls_Script0.pw.println("[temp5]AUTOMATON::> temp5("+l + " " + ") STATE::>"+ _string_temp5(_state_id_temp5, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_temp5==19){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		
		_state_id_temp5 = 17;//moving to state open
		_goto_temp5(_info);
		}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		
		_state_id_temp5 = 16;//moving to state bad
		_goto_temp5(_info);
		}
}
else if (_state_id_temp5==18){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		
		_state_id_temp5 = 20;//moving to state closed
		_goto_temp5(_info);
		}
		else if ((_occurredEvent(_event,0/*floorSummon*/)) && (parent.floorNumber ==l .getFloor ())){
		
		_state_id_temp5 = 19;//moving to state summon
		_goto_temp5(_info);
		}
}
else if (_state_id_temp5==20){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		
		_state_id_temp5 = 17;//moving to state open
		_goto_temp5(_info);
		}
}
else if (_state_id_temp5==17){
		if (1==0){}
		else if ((_occurredEvent(_event,16/*startClosingDoors*/))){
		
		_state_id_temp5 = 18;//moving to state closing
		_goto_temp5(_info);
		}
}
}

public void _goto_temp5(String _info){
_cls_Script0.pw.println("[temp5]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_temp5(_state_id_temp5, 1));
_cls_Script0.pw.flush();
}

public String _string_temp5(int _state_id, int _mode){
switch(_state_id){
case 19: if (_mode == 0) return "summon"; else return "summon";
case 18: if (_mode == 0) return "closing"; else return "closing";
case 16: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 20: if (_mode == 0) return "closed"; else return "closed";
case 17: if (_mode == 0) return "open"; else return "open";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_time1 = 26;

public void _performLogic_time1(String _info, int... _event) {

_cls_Script0.pw.println("[time1]AUTOMATON::> time1("+l + " " + ") STATE::>"+ _string_time1(_state_id_time1, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_time1==25){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (!isNowMoving )){
		
		_state_id_time1 = 26;//moving to state closed
		_goto_time1(_info);
		}
}
else if (_state_id_time1==24){
		if (1==0){}
		else if ((_occurredEvent(_event,20/*clock1*/))){
		
		_state_id_time1 = 21;//moving to state bad
		_goto_time1(_info);
		}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (isNowMoving )){
		
		_state_id_time1 = 25;//moving to state closedAndMoving
		_goto_time1(_info);
		}
}
else if (_state_id_time1==26){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		
		_state_id_time1 = 22;//moving to state open
		_goto_time1(_info);
		}
		else if ((_occurredEvent(_event,18/*floorRequest*/))){
		time1Clock .reset ();

		_state_id_time1 = 24;//moving to state closedPlusReq
		_goto_time1(_info);
		}
		else if ((_occurredEvent(_event,8/*startOrStopMoving*/)) && (isNowMoving )){
		
		_state_id_time1 = 25;//moving to state closedAndMoving
		_goto_time1(_info);
		}
}
else if (_state_id_time1==23){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		time1Clock .reset ();

		_state_id_time1 = 24;//moving to state closedPlusReq
		_goto_time1(_info);
		}
}
else if (_state_id_time1==22){
		if (1==0){}
		else if ((_occurredEvent(_event,14/*closeDoors*/))){
		
		_state_id_time1 = 26;//moving to state closed
		_goto_time1(_info);
		}
		else if ((_occurredEvent(_event,18/*floorRequest*/))){
		
		_state_id_time1 = 23;//moving to state openPlusReq
		_goto_time1(_info);
		}
}
}

public void _goto_time1(String _info){
_cls_Script0.pw.println("[time1]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_time1(_state_id_time1, 1));
_cls_Script0.pw.flush();
}

public String _string_time1(int _state_id, int _mode){
switch(_state_id){
case 21: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 25: if (_mode == 0) return "closedAndMoving"; else return "closedAndMoving";
case 24: if (_mode == 0) return "closedPlusReq"; else return "closedPlusReq";
case 26: if (_mode == 0) return "closed"; else return "closed";
case 23: if (_mode == 0) return "openPlusReq"; else return "openPlusReq";
case 22: if (_mode == 0) return "open"; else return "open";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_time2 = 30;

public void _performLogic_time2(String _info, int... _event) {

_cls_Script0.pw.println("[time2]AUTOMATON::> time2("+l + " " + ") STATE::>"+ _string_time2(_state_id_time2, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_time2==29){
		if (1==0){}
		else if ((_occurredEvent(_event,16/*startClosingDoors*/))){
		
		_state_id_time2 = 30;//moving to state closingOrClosed
		_goto_time2(_info);
		}
}
else if (_state_id_time2==30){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*openDoors*/))){
		time2Clock .reset ();

		_state_id_time2 = 28;//moving to state open
		_goto_time2(_info);
		}
}
else if (_state_id_time2==28){
		if (1==0){}
		else if ((_occurredEvent(_event,16/*startClosingDoors*/))){
		
		_state_id_time2 = 27;//moving to state bad
		_goto_time2(_info);
		}
		else if ((_occurredEvent(_event,22/*clock2*/))){
		
		_state_id_time2 = 29;//moving to state canClose
		_goto_time2(_info);
		}
}
}

public void _goto_time2(String _info){
_cls_Script0.pw.println("[time2]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_time2(_state_id_time2, 1));
_cls_Script0.pw.flush();
}

public String _string_time2(int _state_id, int _mode){
switch(_state_id){
case 29: if (_mode == 0) return "canClose"; else return "canClose";
case 27: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 28: if (_mode == 0) return "open"; else return "open";
case 30: if (_mode == 0) return "closingOrClosed"; else return "closingOrClosed";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
ArrayList<Lift> getBestLifts(Lift[] lifts, int floor, Map<Lift,Integer> destinations) {

final ArrayList<Lift> result = new ArrayList<Lift>();

// Search for moving lifts which will pass through the floor
for (Lift lift : lifts) {
if (destinations.containsKey(lift) && (
(lift.getFloor() <= floor && destinations.get(lift) >= floor) ||
(lift.getFloor() >= floor && destinations.get(lift) <= floor)
)) {
result.add(lift);
}
}

// Search for closest stationary lifts
int distance = -1;
while (result.size() == 0) {
distance++;
for (Lift lift : lifts) {
if (!lift.isMoving() && lift.distanceFromFloor(floor) == distance) {
result.add(lift);
}
}
}

return result;
}
}