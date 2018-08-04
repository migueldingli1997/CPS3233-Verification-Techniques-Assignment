package larva;


import com.liftmania.gui.*;
import com.liftmania.*;
import java.util.*;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_Script0 implements _callable{

public static PrintWriter pw; 
public static _cls_Script0 root;

public static LinkedHashMap<_cls_Script0,_cls_Script0> _cls_Script0_instances = new LinkedHashMap<_cls_Script0,_cls_Script0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("out//output_Script.txt");

root = new _cls_Script0();
_cls_Script0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_Script0 parent; //to remain null - this class does not have a parent!
public static LiftController __1;
public static LiftController __0;
public static LiftController lc;
public static int floorNumber;
public static Lift lift;
public static Object lifts;
int no_automata = 2;
 public Map <Lift ,Integer >destinations =new HashMap <>();
 public Set <Integer >summonsBeingServiced =new HashSet <>();

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_Script0() {
}

public void initialisation() {
}

public static _cls_Script0 _get_cls_Script0_inst() { synchronized(_cls_Script0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_Script0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_Script0_instances){
_performLogic_temp2(_info, _event);
_performLogic_temp3(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_Script1[] a1 = new _cls_Script1[1];
synchronized(_cls_Script1._cls_Script1_instances){
a1 = _cls_Script1._cls_Script1_instances.keySet().toArray(a1);}
for (_cls_Script1 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}

_cls_Script2[] a2 = new _cls_Script2[1];
synchronized(_cls_Script2._cls_Script2_instances){
a2 = _cls_Script2._cls_Script2_instances.keySet().toArray(a2);}
for (_cls_Script2 _inst : a2)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_Script0[] a = new _cls_Script0[1];
synchronized(_cls_Script0_instances){
a = _cls_Script0_instances.keySet().toArray(a);}
for (_cls_Script0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_Script0_instances){
_cls_Script0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_temp2 = 2;

public void _performLogic_temp2(String _info, int... _event) {

_cls_Script0.pw.println("[temp2]AUTOMATON::> temp2("+") STATE::>"+ _string_temp2(_state_id_temp2, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_temp2==1){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*moveLift*/)) && (!summonsBeingServiced .add (floorNumber ))){
		
		_state_id_temp2 = 0;//moving to state bad
		_goto_temp2(_info);
		}
		else if ((_occurredEvent(_event,2/*moveLift*/))){
		
		_state_id_temp2 = 2;//moving to state start
		_goto_temp2(_info);
		}
}
else if (_state_id_temp2==2){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*floorSummon*/))){
		
		_state_id_temp2 = 1;//moving to state summon
		_goto_temp2(_info);
		}
		else if ((_occurredEvent(_event,4/*setFloor*/))){
		summonsBeingServiced .remove (floorNumber );

		_state_id_temp2 = 2;//moving to state start
		_goto_temp2(_info);
		}
}
}

public void _goto_temp2(String _info){
_cls_Script0.pw.println("[temp2]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_temp2(_state_id_temp2, 1));
_cls_Script0.pw.flush();
}

public String _string_temp2(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "summon"; else return "summon";
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 2: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_temp3 = 4;

public void _performLogic_temp3(String _info, int... _event) {

_cls_Script0.pw.println("[temp3]AUTOMATON::> temp3("+") STATE::>"+ _string_temp3(_state_id_temp3, 0));
_cls_Script0.pw.flush();

if (0==1){}
else if (_state_id_temp3==4){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*getClosest*/)) && (!getBestLifts (lc .getLifts (),floorNumber ,destinations ).equals (lifts ))){
		
		_state_id_temp3 = 3;//moving to state bad
		_goto_temp3(_info);
		}
		else if ((_occurredEvent(_event,2/*moveLift*/))){
		destinations .put (lift ,floorNumber );

		_state_id_temp3 = 4;//moving to state start
		_goto_temp3(_info);
		}
		else if ((_occurredEvent(_event,4/*setFloor*/)) && (floorNumber ==destinations .getOrDefault (lift ,-1 ))){
		destinations .remove (lift );

		_state_id_temp3 = 4;//moving to state start
		_goto_temp3(_info);
		}
}
}

public void _goto_temp3(String _info){
_cls_Script0.pw.println("[temp3]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_temp3(_state_id_temp3, 1));
_cls_Script0.pw.flush();
}

public String _string_temp3(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionScript().toString()+" ";
case 4: if (_mode == 0) return "start"; else return "start";
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