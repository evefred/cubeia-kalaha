// I AM AUTO-GENERATED, DON'T CHECK ME INTO SUBVERSION (or else...)

var FB_PROTOCOL = FB_PROTOCOL || {};


FB_PROTOCOL.Attribute=function(){this.classId=function(){return FB_PROTOCOL.Attribute.CLASSID
};
this.name={};
this.value={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeString(this.name);
a.writeString(this.value);
return a
};
this.load=function(a){this.name=a.readString();
this.value=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.Attribute :";
a+=" name["+this.name.toString()+"]";
a+=" value["+this.value.toString()+"]";
return a
}
};
FB_PROTOCOL.Attribute.CLASSID=8;FB_PROTOCOL.BadPacket=function(){this.classId=function(){return FB_PROTOCOL.BadPacket.CLASSID
};
this.cmd={};
this.error={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeByte(this.cmd);
a.writeByte(this.error);
return a
};
this.load=function(a){this.cmd=a.readByte();
this.error=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.BadPacket :";
a+=" cmd["+this.cmd.toString()+"]";
a+=" error["+this.error.toString()+"]";
return a
}
};
FB_PROTOCOL.BadPacket.CLASSID=3;FB_PROTOCOL.ChannelChatPacket=function(){this.classId=function(){return FB_PROTOCOL.ChannelChatPacket.CLASSID
};
this.channelid={};
this.targetid={};
this.message={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.channelid);
a.writeInt(this.targetid);
a.writeString(this.message);
return a
};
this.load=function(a){this.channelid=a.readInt();
this.targetid=a.readInt();
this.message=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.ChannelChatPacket :";
a+=" channelid["+this.channelid.toString()+"]";
a+=" targetid["+this.targetid.toString()+"]";
a+=" message["+this.message.toString()+"]";
return a
}
};
FB_PROTOCOL.ChannelChatPacket.CLASSID=124;FB_PROTOCOL.CreateTableRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.CreateTableRequestPacket.CLASSID
};
this.seq={};
this.gameid={};
this.seats={};
this.params=[];
this.invitees=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.gameid);
a.writeByte(this.seats);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}a.writeInt(this.invitees.length);
for(b=0;
b<this.invitees.length;
b++){a.writeInt(this.invitees[b])
}return a
};
this.load=function(b){this.seq=b.readInt();
this.gameid=b.readInt();
this.seats=b.readByte();
var c;
var e=b.readInt();
var d;
this.params=[];
for(c=0;
c<e;
c++){d=new FB_PROTOCOL.Param();
d.load(b);
this.params.push(d)
}var a=b.readInt();
this.invitees=[];
for(c=0;
c<a;
c++){this.invitees.push(b.readInt())
}};
this.toString=function(){var a="FB_PROTOCOL.CreateTableRequestPacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" seats["+this.seats.toString()+"]";
a+=" params["+this.params.toString()+"]";
a+=" invitees["+this.invitees.toString()+"]";
return a
}
};
FB_PROTOCOL.CreateTableRequestPacket.CLASSID=40;FB_PROTOCOL.CreateTableResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.CreateTableResponsePacket.CLASSID
};
this.seq={};
this.tableid={};
this.seat={};
this.status={};
this.code={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.tableid);
a.writeByte(this.seat);
a.writeUnsignedByte(this.status);
a.writeInt(this.code);
return a
};
this.load=function(a){this.seq=a.readInt();
this.tableid=a.readInt();
this.seat=a.readByte();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte());
this.code=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.CreateTableResponsePacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" code["+this.code.toString()+"]";
return a
}
};
FB_PROTOCOL.CreateTableResponsePacket.CLASSID=41;FB_PROTOCOL.EncryptedTransportPacket=function(){this.classId=function(){return FB_PROTOCOL.EncryptedTransportPacket.CLASSID
};
this.func={};
this.payload=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeByte(this.func);
a.writeInt(this.payload.length);
a.writeArray(this.payload);
return a
};
this.load=function(a){this.func=a.readByte();
var b=a.readInt();
this.payload=a.readArray(b)
};
this.toString=function(){var a="FB_PROTOCOL.EncryptedTransportPacket :";
a+=" func["+this.func.toString()+"]";
a+=" payload["+this.payload.toString()+"]";
return a
}
};
FB_PROTOCOL.EncryptedTransportPacket.CLASSID=105;FB_PROTOCOL.FilteredJoinCancelRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.FilteredJoinCancelRequestPacket.CLASSID
};
this.seq={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
return a
};
this.load=function(a){this.seq=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.FilteredJoinCancelRequestPacket :";
a+=" seq["+this.seq.toString()+"]";
return a
}
};
FB_PROTOCOL.FilteredJoinCancelRequestPacket.CLASSID=172;FB_PROTOCOL.FilteredJoinCancelResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.FilteredJoinCancelResponsePacket.CLASSID
};
this.seq={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.seq=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.FilteredJoinCancelResponsePacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.FilteredJoinCancelResponsePacket.CLASSID=173;FB_PROTOCOL.FilteredJoinResponseStatusEnum=function(){};
FB_PROTOCOL.FilteredJoinResponseStatusEnum.OK=0;
FB_PROTOCOL.FilteredJoinResponseStatusEnum.FAILED=1;
FB_PROTOCOL.FilteredJoinResponseStatusEnum.DENIED=2;
FB_PROTOCOL.FilteredJoinResponseStatusEnum.SEATING=3;
FB_PROTOCOL.FilteredJoinResponseStatusEnum.WAIT_LIST=4;
FB_PROTOCOL.FilteredJoinResponseStatusEnum.makeFilteredJoinResponseStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.FilteredJoinResponseStatusEnum.OK;
case 1:return FB_PROTOCOL.FilteredJoinResponseStatusEnum.FAILED;
case 2:return FB_PROTOCOL.FilteredJoinResponseStatusEnum.DENIED;
case 3:return FB_PROTOCOL.FilteredJoinResponseStatusEnum.SEATING;
case 4:return FB_PROTOCOL.FilteredJoinResponseStatusEnum.WAIT_LIST
}return -1
};FB_PROTOCOL.FilteredJoinTableAvailablePacket=function(){this.classId=function(){return FB_PROTOCOL.FilteredJoinTableAvailablePacket.CLASSID
};
this.seq={};
this.tableid={};
this.seat={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.tableid);
a.writeByte(this.seat);
return a
};
this.load=function(a){this.seq=a.readInt();
this.tableid=a.readInt();
this.seat=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.FilteredJoinTableAvailablePacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
return a
}
};
FB_PROTOCOL.FilteredJoinTableAvailablePacket.CLASSID=174;FB_PROTOCOL.FilteredJoinTableRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.FilteredJoinTableRequestPacket.CLASSID
};
this.seq={};
this.gameid={};
this.address={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.gameid);
a.writeString(this.address);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.seq=a.readInt();
this.gameid=a.readInt();
this.address=a.readString();
var b;
var c=a.readInt();
var d;
this.params=[];
for(b=0;
b<c;
b++){d=new FB_PROTOCOL.ParamFilter();
d.load(a);
this.params.push(d)
}};
this.toString=function(){var a="FB_PROTOCOL.FilteredJoinTableRequestPacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.FilteredJoinTableRequestPacket.CLASSID=170;FB_PROTOCOL.FilteredJoinTableResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.FilteredJoinTableResponsePacket.CLASSID
};
this.seq={};
this.gameid={};
this.address={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.gameid);
a.writeString(this.address);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.seq=a.readInt();
this.gameid=a.readInt();
this.address=a.readString();
this.status=FB_PROTOCOL.FilteredJoinResponseStatusEnum.makeFilteredJoinResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.FilteredJoinTableResponsePacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.FilteredJoinTableResponsePacket.CLASSID=171;FB_PROTOCOL.ForcedLogoutPacket=function(){this.classId=function(){return FB_PROTOCOL.ForcedLogoutPacket.CLASSID
};
this.code={};
this.message={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.code);
a.writeString(this.message);
return a
};
this.load=function(a){this.code=a.readInt();
this.message=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.ForcedLogoutPacket :";
a+=" code["+this.code.toString()+"]";
a+=" message["+this.message.toString()+"]";
return a
}
};
FB_PROTOCOL.ForcedLogoutPacket.CLASSID=14;FB_PROTOCOL.GameTransportPacket=function(){this.classId=function(){return FB_PROTOCOL.GameTransportPacket.CLASSID
};
this.tableid={};
this.pid={};
this.gamedata=[];
this.attributes=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeInt(this.pid);
a.writeInt(this.gamedata.length);
a.writeArray(this.gamedata);
a.writeInt(this.attributes.length);
var b;
for(b=0;
b<this.attributes.length;
b++){a.writeArray(this.attributes[b].save())
}return a
};
this.load=function(a){this.tableid=a.readInt();
this.pid=a.readInt();
var d=a.readInt();
this.gamedata=a.readArray(d);
var b;
var c=a.readInt();
var e;
this.attributes=[];
for(b=0;
b<c;
b++){e=new FB_PROTOCOL.Attribute();
e.load(a);
this.attributes.push(e)
}};
this.toString=function(){var a="FB_PROTOCOL.GameTransportPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
a+=" gamedata["+this.gamedata.toString()+"]";
a+=" attributes["+this.attributes.toString()+"]";
return a
}
};
FB_PROTOCOL.GameTransportPacket.CLASSID=100;FB_PROTOCOL.GameVersionPacket=function(){this.classId=function(){return FB_PROTOCOL.GameVersionPacket.CLASSID
};
this.game={};
this.operatorid={};
this.version={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.game);
a.writeInt(this.operatorid);
a.writeString(this.version);
return a
};
this.load=function(a){this.game=a.readInt();
this.operatorid=a.readInt();
this.version=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.GameVersionPacket :";
a+=" game["+this.game.toString()+"]";
a+=" operatorid["+this.operatorid.toString()+"]";
a+=" version["+this.version.toString()+"]";
return a
}
};
FB_PROTOCOL.GameVersionPacket.CLASSID=1;FB_PROTOCOL.GoodPacket=function(){this.classId=function(){return FB_PROTOCOL.GoodPacket.CLASSID
};
this.cmd={};
this.extra={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeByte(this.cmd);
a.writeInt(this.extra);
return a
};
this.load=function(a){this.cmd=a.readByte();
this.extra=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.GoodPacket :";
a+=" cmd["+this.cmd.toString()+"]";
a+=" extra["+this.extra.toString()+"]";
return a
}
};
FB_PROTOCOL.GoodPacket.CLASSID=2;FB_PROTOCOL.InvitePlayersRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.InvitePlayersRequestPacket.CLASSID
};
this.tableid={};
this.invitees=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeInt(this.invitees.length);
var b;
for(b=0;
b<this.invitees.length;
b++){a.writeInt(this.invitees[b])
}return a
};
this.load=function(b){this.tableid=b.readInt();
var c;
var a=b.readInt();
this.invitees=[];
for(c=0;
c<a;
c++){this.invitees.push(b.readInt())
}};
this.toString=function(){var a="FB_PROTOCOL.InvitePlayersRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" invitees["+this.invitees.toString()+"]";
return a
}
};
FB_PROTOCOL.InvitePlayersRequestPacket.CLASSID=42;FB_PROTOCOL.JoinChatChannelRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.JoinChatChannelRequestPacket.CLASSID
};
this.channelid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.channelid);
return a
};
this.load=function(a){this.channelid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.JoinChatChannelRequestPacket :";
a+=" channelid["+this.channelid.toString()+"]";
return a
}
};
FB_PROTOCOL.JoinChatChannelRequestPacket.CLASSID=120;FB_PROTOCOL.JoinChatChannelResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.JoinChatChannelResponsePacket.CLASSID
};
this.channelid={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.channelid);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.channelid=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.JoinChatChannelResponsePacket :";
a+=" channelid["+this.channelid.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.JoinChatChannelResponsePacket.CLASSID=121;FB_PROTOCOL.JoinRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.JoinRequestPacket.CLASSID
};
this.tableid={};
this.seat={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeByte(this.seat);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.tableid=a.readInt();
this.seat=a.readByte();
var b;
var d=a.readInt();
var c;
this.params=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.params.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.JoinRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.JoinRequestPacket.CLASSID=30;FB_PROTOCOL.JoinResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.JoinResponsePacket.CLASSID
};
this.tableid={};
this.seat={};
this.status={};
this.code={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeByte(this.seat);
a.writeUnsignedByte(this.status);
a.writeInt(this.code);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.seat=a.readByte();
this.status=FB_PROTOCOL.JoinResponseStatusEnum.makeJoinResponseStatusEnum(a.readUnsignedByte());
this.code=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.JoinResponsePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" code["+this.code.toString()+"]";
return a
}
};
FB_PROTOCOL.JoinResponsePacket.CLASSID=31;FB_PROTOCOL.JoinResponseStatusEnum=function(){};
FB_PROTOCOL.JoinResponseStatusEnum.OK=0;
FB_PROTOCOL.JoinResponseStatusEnum.FAILED=1;
FB_PROTOCOL.JoinResponseStatusEnum.DENIED=2;
FB_PROTOCOL.JoinResponseStatusEnum.makeJoinResponseStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.JoinResponseStatusEnum.OK;
case 1:return FB_PROTOCOL.JoinResponseStatusEnum.FAILED;
case 2:return FB_PROTOCOL.JoinResponseStatusEnum.DENIED
}return -1
};FB_PROTOCOL.KickPlayerPacket=function(){this.classId=function(){return FB_PROTOCOL.KickPlayerPacket.CLASSID
};
this.tableid={};
this.reasonCode={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeShort(this.reasonCode);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.reasonCode=a.readShort()
};
this.toString=function(){var a="FB_PROTOCOL.KickPlayerPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" reasonCode["+this.reasonCode.toString()+"]";
return a
}
};
FB_PROTOCOL.KickPlayerPacket.CLASSID=64;FB_PROTOCOL.LeaveChatChannelPacket=function(){this.classId=function(){return FB_PROTOCOL.LeaveChatChannelPacket.CLASSID
};
this.channelid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.channelid);
return a
};
this.load=function(a){this.channelid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.LeaveChatChannelPacket :";
a+=" channelid["+this.channelid.toString()+"]";
return a
}
};
FB_PROTOCOL.LeaveChatChannelPacket.CLASSID=122;FB_PROTOCOL.LeaveRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.LeaveRequestPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.LeaveRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.LeaveRequestPacket.CLASSID=36;FB_PROTOCOL.LeaveResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.LeaveResponsePacket.CLASSID
};
this.tableid={};
this.status={};
this.code={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeUnsignedByte(this.status);
a.writeInt(this.code);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte());
this.code=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.LeaveResponsePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" code["+this.code.toString()+"]";
return a
}
};
FB_PROTOCOL.LeaveResponsePacket.CLASSID=37;FB_PROTOCOL.LobbyObjectSubscribePacket=function(){this.classId=function(){return FB_PROTOCOL.LobbyObjectSubscribePacket.CLASSID
};
this.type={};
this.gameid={};
this.address={};
this.objectid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeUnsignedByte(this.type);
a.writeInt(this.gameid);
a.writeString(this.address);
a.writeInt(this.objectid);
return a
};
this.load=function(a){this.type=FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum(a.readUnsignedByte());
this.gameid=a.readInt();
this.address=a.readString();
this.objectid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.LobbyObjectSubscribePacket :";
a+=" type["+this.type.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" objectid["+this.objectid.toString()+"]";
return a
}
};
FB_PROTOCOL.LobbyObjectSubscribePacket.CLASSID=151;FB_PROTOCOL.LobbyObjectUnsubscribePacket=function(){this.classId=function(){return FB_PROTOCOL.LobbyObjectUnsubscribePacket.CLASSID
};
this.type={};
this.gameid={};
this.address={};
this.objectid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeUnsignedByte(this.type);
a.writeInt(this.gameid);
a.writeString(this.address);
a.writeInt(this.objectid);
return a
};
this.load=function(a){this.type=FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum(a.readUnsignedByte());
this.gameid=a.readInt();
this.address=a.readString();
this.objectid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.LobbyObjectUnsubscribePacket :";
a+=" type["+this.type.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" objectid["+this.objectid.toString()+"]";
return a
}
};
FB_PROTOCOL.LobbyObjectUnsubscribePacket.CLASSID=152;FB_PROTOCOL.LobbyQueryPacket=function(){this.classId=function(){return FB_PROTOCOL.LobbyQueryPacket.CLASSID
};
this.gameid={};
this.address={};
this.type={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.gameid);
a.writeString(this.address);
a.writeUnsignedByte(this.type);
return a
};
this.load=function(a){this.gameid=a.readInt();
this.address=a.readString();
this.type=FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.LobbyQueryPacket :";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" type["+this.type.toString()+"]";
return a
}
};
FB_PROTOCOL.LobbyQueryPacket.CLASSID=142;FB_PROTOCOL.LobbySubscribePacket=function(){this.classId=function(){return FB_PROTOCOL.LobbySubscribePacket.CLASSID
};
this.type={};
this.gameid={};
this.address={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeUnsignedByte(this.type);
a.writeInt(this.gameid);
a.writeString(this.address);
return a
};
this.load=function(a){this.type=FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum(a.readUnsignedByte());
this.gameid=a.readInt();
this.address=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.LobbySubscribePacket :";
a+=" type["+this.type.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
return a
}
};
FB_PROTOCOL.LobbySubscribePacket.CLASSID=145;FB_PROTOCOL.LobbyTypeEnum=function(){};
FB_PROTOCOL.LobbyTypeEnum.REGULAR=0;
FB_PROTOCOL.LobbyTypeEnum.MTT=1;
FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum=function(a){switch(a){case 0:return FB_PROTOCOL.LobbyTypeEnum.REGULAR;
case 1:return FB_PROTOCOL.LobbyTypeEnum.MTT
}return -1
};FB_PROTOCOL.LobbyUnsubscribePacket=function(){this.classId=function(){return FB_PROTOCOL.LobbyUnsubscribePacket.CLASSID
};
this.type={};
this.gameid={};
this.address={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeUnsignedByte(this.type);
a.writeInt(this.gameid);
a.writeString(this.address);
return a
};
this.load=function(a){this.type=FB_PROTOCOL.LobbyTypeEnum.makeLobbyTypeEnum(a.readUnsignedByte());
this.gameid=a.readInt();
this.address=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.LobbyUnsubscribePacket :";
a+=" type["+this.type.toString()+"]";
a+=" gameid["+this.gameid.toString()+"]";
a+=" address["+this.address.toString()+"]";
return a
}
};
FB_PROTOCOL.LobbyUnsubscribePacket.CLASSID=146;FB_PROTOCOL.LocalServiceTransportPacket=function(){this.classId=function(){return FB_PROTOCOL.LocalServiceTransportPacket.CLASSID
};
this.seq={};
this.servicedata=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.seq);
a.writeInt(this.servicedata.length);
a.writeArray(this.servicedata);
return a
};
this.load=function(a){this.seq=a.readInt();
var b=a.readInt();
this.servicedata=a.readArray(b)
};
this.toString=function(){var a="FB_PROTOCOL.LocalServiceTransportPacket :";
a+=" seq["+this.seq.toString()+"]";
a+=" servicedata["+this.servicedata.toString()+"]";
return a
}
};
FB_PROTOCOL.LocalServiceTransportPacket.CLASSID=103;FB_PROTOCOL.LoginRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.LoginRequestPacket.CLASSID
};
this.user={};
this.password={};
this.operatorid={};
this.credentials=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeString(this.user);
a.writeString(this.password);
a.writeInt(this.operatorid);
a.writeInt(this.credentials.length);
a.writeArray(this.credentials);
return a
};
this.load=function(a){this.user=a.readString();
this.password=a.readString();
this.operatorid=a.readInt();
var b=a.readInt();
this.credentials=a.readArray(b)
};
this.toString=function(){var a="FB_PROTOCOL.LoginRequestPacket :";
a+=" user["+this.user.toString()+"]";
a+=" password["+this.password.toString()+"]";
a+=" operatorid["+this.operatorid.toString()+"]";
a+=" credentials["+this.credentials.toString()+"]";
return a
}
};
FB_PROTOCOL.LoginRequestPacket.CLASSID=10;FB_PROTOCOL.LoginResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.LoginResponsePacket.CLASSID
};
this.screenname={};
this.pid={};
this.status={};
this.code={};
this.message={};
this.credentials=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeString(this.screenname);
a.writeInt(this.pid);
a.writeUnsignedByte(this.status);
a.writeInt(this.code);
a.writeString(this.message);
a.writeInt(this.credentials.length);
a.writeArray(this.credentials);
return a
};
this.load=function(a){this.screenname=a.readString();
this.pid=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte());
this.code=a.readInt();
this.message=a.readString();
var b=a.readInt();
this.credentials=a.readArray(b)
};
this.toString=function(){var a="FB_PROTOCOL.LoginResponsePacket :";
a+=" screenname["+this.screenname.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" code["+this.code.toString()+"]";
a+=" message["+this.message.toString()+"]";
a+=" credentials["+this.credentials.toString()+"]";
return a
}
};
FB_PROTOCOL.LoginResponsePacket.CLASSID=11;FB_PROTOCOL.LogoutPacket=function(){this.classId=function(){return FB_PROTOCOL.LogoutPacket.CLASSID
};
this.leaveTables={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeBoolean(this.leaveTables);
return a
};
this.load=function(a){this.leaveTables=a.readBoolean()
};
this.toString=function(){var a="FB_PROTOCOL.LogoutPacket :";
a+=" leave_tables["+this.leaveTables.toString()+"]";
return a
}
};
FB_PROTOCOL.LogoutPacket.CLASSID=12;FB_PROTOCOL.MttPickedUpPacket=function(){this.classId=function(){return FB_PROTOCOL.MttPickedUpPacket.CLASSID
};
this.mttid={};
this.tableid={};
this.keepWatching={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeInt(this.tableid);
a.writeBoolean(this.keepWatching);
return a
};
this.load=function(a){this.mttid=a.readInt();
this.tableid=a.readInt();
this.keepWatching=a.readBoolean()
};
this.toString=function(){var a="FB_PROTOCOL.MttPickedUpPacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" keep_watching["+this.keepWatching.toString()+"]";
return a
}
};
FB_PROTOCOL.MttPickedUpPacket.CLASSID=210;FB_PROTOCOL.MttRegisterRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.MttRegisterRequestPacket.CLASSID
};
this.mttid={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.mttid=a.readInt();
var b;
var d=a.readInt();
var c;
this.params=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.params.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.MttRegisterRequestPacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.MttRegisterRequestPacket.CLASSID=205;FB_PROTOCOL.MttRegisterResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.MttRegisterResponsePacket.CLASSID
};
this.mttid={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.mttid=a.readInt();
this.status=FB_PROTOCOL.TournamentRegisterResponseStatusEnum.makeTournamentRegisterResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.MttRegisterResponsePacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.MttRegisterResponsePacket.CLASSID=206;FB_PROTOCOL.MttSeatedPacket=function(){this.classId=function(){return FB_PROTOCOL.MttSeatedPacket.CLASSID
};
this.mttid={};
this.tableid={};
this.seat={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeInt(this.tableid);
a.writeByte(this.seat);
return a
};
this.load=function(a){this.mttid=a.readInt();
this.tableid=a.readInt();
this.seat=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.MttSeatedPacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
return a
}
};
FB_PROTOCOL.MttSeatedPacket.CLASSID=209;FB_PROTOCOL.MttTransportPacket=function(){this.classId=function(){return FB_PROTOCOL.MttTransportPacket.CLASSID
};
this.mttid={};
this.pid={};
this.mttdata=[];
this.attributes=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeInt(this.pid);
a.writeInt(this.mttdata.length);
a.writeArray(this.mttdata);
a.writeInt(this.attributes.length);
var b;
for(b=0;
b<this.attributes.length;
b++){a.writeArray(this.attributes[b].save())
}return a
};
this.load=function(a){this.mttid=a.readInt();
this.pid=a.readInt();
var b=a.readInt();
this.mttdata=a.readArray(b);
var c;
var d=a.readInt();
var e;
this.attributes=[];
for(c=0;
c<d;
c++){e=new FB_PROTOCOL.Attribute();
e.load(a);
this.attributes.push(e)
}};
this.toString=function(){var a="FB_PROTOCOL.MttTransportPacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
a+=" mttdata["+this.mttdata.toString()+"]";
a+=" attributes["+this.attributes.toString()+"]";
return a
}
};
FB_PROTOCOL.MttTransportPacket.CLASSID=104;FB_PROTOCOL.MttUnregisterRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.MttUnregisterRequestPacket.CLASSID
};
this.mttid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
return a
};
this.load=function(a){this.mttid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.MttUnregisterRequestPacket :";
a+=" mttid["+this.mttid.toString()+"]";
return a
}
};
FB_PROTOCOL.MttUnregisterRequestPacket.CLASSID=207;FB_PROTOCOL.MttUnregisterResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.MttUnregisterResponsePacket.CLASSID
};
this.mttid={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.mttid=a.readInt();
this.status=FB_PROTOCOL.TournamentRegisterResponseStatusEnum.makeTournamentRegisterResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.MttUnregisterResponsePacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.MttUnregisterResponsePacket.CLASSID=208;FB_PROTOCOL.NotifyChannelChatPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyChannelChatPacket.CLASSID
};
this.pid={};
this.channelid={};
this.targetid={};
this.nick={};
this.message={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.pid);
a.writeInt(this.channelid);
a.writeInt(this.targetid);
a.writeString(this.nick);
a.writeString(this.message);
return a
};
this.load=function(a){this.pid=a.readInt();
this.channelid=a.readInt();
this.targetid=a.readInt();
this.nick=a.readString();
this.message=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.NotifyChannelChatPacket :";
a+=" pid["+this.pid.toString()+"]";
a+=" channelid["+this.channelid.toString()+"]";
a+=" targetid["+this.targetid.toString()+"]";
a+=" nick["+this.nick.toString()+"]";
a+=" message["+this.message.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyChannelChatPacket.CLASSID=123;FB_PROTOCOL.NotifyInvitedPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyInvitedPacket.CLASSID
};
this.inviter={};
this.screenname={};
this.tableid={};
this.seat={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.inviter);
a.writeString(this.screenname);
a.writeInt(this.tableid);
a.writeByte(this.seat);
return a
};
this.load=function(a){this.inviter=a.readInt();
this.screenname=a.readString();
this.tableid=a.readInt();
this.seat=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.NotifyInvitedPacket :";
a+=" inviter["+this.inviter.toString()+"]";
a+=" screenname["+this.screenname.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyInvitedPacket.CLASSID=43;FB_PROTOCOL.NotifyJoinPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyJoinPacket.CLASSID
};
this.tableid={};
this.pid={};
this.nick={};
this.seat={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeInt(this.pid);
a.writeString(this.nick);
a.writeByte(this.seat);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.pid=a.readInt();
this.nick=a.readString();
this.seat=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.NotifyJoinPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
a+=" nick["+this.nick.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyJoinPacket.CLASSID=60;FB_PROTOCOL.NotifyLeavePacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyLeavePacket.CLASSID
};
this.tableid={};
this.pid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeInt(this.pid);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.pid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.NotifyLeavePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyLeavePacket.CLASSID=61;FB_PROTOCOL.NotifyRegisteredPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyRegisteredPacket.CLASSID
};
this.tournaments=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tournaments.length);
var b;
for(b=0;
b<this.tournaments.length;
b++){a.writeInt(this.tournaments[b])
}return a
};
this.load=function(b){var c;
var a=b.readInt();
this.tournaments=[];
for(c=0;
c<a;
c++){this.tournaments.push(b.readInt())
}};
this.toString=function(){var a="FB_PROTOCOL.NotifyRegisteredPacket :";
a+=" tournaments["+this.tournaments.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyRegisteredPacket.CLASSID=211;FB_PROTOCOL.NotifySeatedPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifySeatedPacket.CLASSID
};
this.tableid={};
this.seat={};
this.mttid={};
this.snapshot={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeByte(this.seat);
a.writeInt(this.mttid);
a.writeArray(this.snapshot.save());
return a
};
this.load=function(a){this.tableid=a.readInt();
this.seat=a.readByte();
this.mttid=a.readInt();
this.snapshot=new FB_PROTOCOL.TableSnapshotPacket();
this.snapshot.load(a)
};
this.toString=function(){var a="FB_PROTOCOL.NotifySeatedPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
a+=" mttid["+this.mttid.toString()+"]";
a+=" snapshot["+this.snapshot.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifySeatedPacket.CLASSID=62;FB_PROTOCOL.NotifyWatchingPacket=function(){this.classId=function(){return FB_PROTOCOL.NotifyWatchingPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.NotifyWatchingPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.NotifyWatchingPacket.CLASSID=63;FB_PROTOCOL.Param=function(){this.classId=function(){return FB_PROTOCOL.Param.CLASSID
};
this.key={};
this.type={};
this.value=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeString(this.key);
a.writeByte(this.type);
a.writeInt(this.value.length);
a.writeArray(this.value);
return a
};
this.load=function(b){this.key=b.readString();
this.type=b.readByte();
var a=b.readInt();
this.value=b.readArray(a)
};
this.toString=function(){var a="FB_PROTOCOL.Param :";
a+=" key["+this.key.toString()+"]";
a+=" type["+this.type.toString()+"]";
a+=" value["+this.value.toString()+"]";
return a
}
};
FB_PROTOCOL.Param.CLASSID=5;FB_PROTOCOL.ParamFilter=function(){this.classId=function(){return FB_PROTOCOL.ParamFilter.CLASSID
};
this.param={};
this.op={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeArray(this.param.save());
a.writeByte(this.op);
return a
};
this.load=function(a){this.param=new FB_PROTOCOL.Param();
this.param.load(a);
this.op=a.readByte()
};
this.toString=function(){var a="FB_PROTOCOL.ParamFilter :";
a+=" param["+this.param.toString()+"]";
a+=" op["+this.op.toString()+"]";
return a
}
};
FB_PROTOCOL.ParamFilter.CLASSID=6;FB_PROTOCOL.ParameterFilterEnum=function(){};
FB_PROTOCOL.ParameterFilterEnum.EQUALS=0;
FB_PROTOCOL.ParameterFilterEnum.GREATER_THAN=1;
FB_PROTOCOL.ParameterFilterEnum.SMALLER_THAN=2;
FB_PROTOCOL.ParameterFilterEnum.EQUALS_OR_GREATER_THAN=3;
FB_PROTOCOL.ParameterFilterEnum.EQUALS_OR_SMALLER_THAN=4;
FB_PROTOCOL.ParameterFilterEnum.makeParameterFilterEnum=function(a){switch(a){case 0:return FB_PROTOCOL.ParameterFilterEnum.EQUALS;
case 1:return FB_PROTOCOL.ParameterFilterEnum.GREATER_THAN;
case 2:return FB_PROTOCOL.ParameterFilterEnum.SMALLER_THAN;
case 3:return FB_PROTOCOL.ParameterFilterEnum.EQUALS_OR_GREATER_THAN;
case 4:return FB_PROTOCOL.ParameterFilterEnum.EQUALS_OR_SMALLER_THAN
}return -1
};FB_PROTOCOL.ParameterTypeEnum=function(){};
FB_PROTOCOL.ParameterTypeEnum.STRING=0;
FB_PROTOCOL.ParameterTypeEnum.INT=1;
FB_PROTOCOL.ParameterTypeEnum.DATE=2;
FB_PROTOCOL.ParameterTypeEnum.makeParameterTypeEnum=function(a){switch(a){case 0:return FB_PROTOCOL.ParameterTypeEnum.STRING;
case 1:return FB_PROTOCOL.ParameterTypeEnum.INT;
case 2:return FB_PROTOCOL.ParameterTypeEnum.DATE
}return -1
};FB_PROTOCOL.PingPacket=function(){this.classId=function(){return FB_PROTOCOL.PingPacket.CLASSID
};
this.id={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.id);
return a
};
this.load=function(a){this.id=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.PingPacket :";
a+=" id["+this.id.toString()+"]";
return a
}
};
FB_PROTOCOL.PingPacket.CLASSID=7;FB_PROTOCOL.PlayerInfoPacket=function(){this.classId=function(){return FB_PROTOCOL.PlayerInfoPacket.CLASSID
};
this.pid={};
this.nick={};
this.details=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.pid);
a.writeString(this.nick);
a.writeInt(this.details.length);
var b;
for(b=0;
b<this.details.length;
b++){a.writeArray(this.details[b].save())
}return a
};
this.load=function(a){this.pid=a.readInt();
this.nick=a.readString();
var b;
var d=a.readInt();
var c;
this.details=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.details.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.PlayerInfoPacket :";
a+=" pid["+this.pid.toString()+"]";
a+=" nick["+this.nick.toString()+"]";
a+=" details["+this.details.toString()+"]";
return a
}
};
FB_PROTOCOL.PlayerInfoPacket.CLASSID=13;FB_PROTOCOL.PlayerQueryRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.PlayerQueryRequestPacket.CLASSID
};
this.pid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.pid);
return a
};
this.load=function(a){this.pid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.PlayerQueryRequestPacket :";
a+=" pid["+this.pid.toString()+"]";
return a
}
};
FB_PROTOCOL.PlayerQueryRequestPacket.CLASSID=16;FB_PROTOCOL.PlayerQueryResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.PlayerQueryResponsePacket.CLASSID
};
this.pid={};
this.nick={};
this.status={};
this.data=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.pid);
a.writeString(this.nick);
a.writeUnsignedByte(this.status);
a.writeInt(this.data.length);
a.writeArray(this.data);
return a
};
this.load=function(a){this.pid=a.readInt();
this.nick=a.readString();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte());
var b=a.readInt();
this.data=a.readArray(b)
};
this.toString=function(){var a="FB_PROTOCOL.PlayerQueryResponsePacket :";
a+=" pid["+this.pid.toString()+"]";
a+=" nick["+this.nick.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" data["+this.data.toString()+"]";
return a
}
};
FB_PROTOCOL.PlayerQueryResponsePacket.CLASSID=17;FB_PROTOCOL.PlayerStatusEnum=function(){};
FB_PROTOCOL.PlayerStatusEnum.CONNECTED=0;
FB_PROTOCOL.PlayerStatusEnum.WAITING_REJOIN=1;
FB_PROTOCOL.PlayerStatusEnum.DISCONNECTED=2;
FB_PROTOCOL.PlayerStatusEnum.LEAVING=3;
FB_PROTOCOL.PlayerStatusEnum.TABLE_LOCAL=4;
FB_PROTOCOL.PlayerStatusEnum.RESERVATION=5;
FB_PROTOCOL.PlayerStatusEnum.makePlayerStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.PlayerStatusEnum.CONNECTED;
case 1:return FB_PROTOCOL.PlayerStatusEnum.WAITING_REJOIN;
case 2:return FB_PROTOCOL.PlayerStatusEnum.DISCONNECTED;
case 3:return FB_PROTOCOL.PlayerStatusEnum.LEAVING;
case 4:return FB_PROTOCOL.PlayerStatusEnum.TABLE_LOCAL;
case 5:return FB_PROTOCOL.PlayerStatusEnum.RESERVATION
}return -1
};FB_PROTOCOL.ProbePacket=function(){this.classId=function(){return FB_PROTOCOL.ProbePacket.CLASSID
};
this.id={};
this.tableid={};
this.stamps=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.id);
a.writeInt(this.tableid);
a.writeInt(this.stamps.length);
var b;
for(b=0;
b<this.stamps.length;
b++){a.writeArray(this.stamps[b].save())
}return a
};
this.load=function(a){this.id=a.readInt();
this.tableid=a.readInt();
var c;
var b=a.readInt();
var d;
this.stamps=[];
for(c=0;
c<b;
c++){d=new FB_PROTOCOL.ProbeStamp();
d.load(a);
this.stamps.push(d)
}};
this.toString=function(){var a="FB_PROTOCOL.ProbePacket :";
a+=" id["+this.id.toString()+"]";
a+=" tableid["+this.tableid.toString()+"]";
a+=" stamps["+this.stamps.toString()+"]";
return a
}
};
FB_PROTOCOL.ProbePacket.CLASSID=201;FB_PROTOCOL.ProbeStamp=function(){this.classId=function(){return FB_PROTOCOL.ProbeStamp.CLASSID
};
this.clazz={};
this.timestamp={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeString(this.clazz);
a.writeLong(this.timestamp);
return a
};
this.load=function(a){this.clazz=a.readString();
this.timestamp=a.readLong()
};
this.toString=function(){var a="FB_PROTOCOL.ProbeStamp :";
a+=" clazz["+this.clazz.toString()+"]";
a+=" timestamp["+this.timestamp.toString()+"]";
return a
}
};
FB_PROTOCOL.ProbeStamp.CLASSID=200;FB_PROTOCOL.ProtocolObjectFactory={};
FB_PROTOCOL.ProtocolObjectFactory.create=function(c,a){var b;
switch(c){case FB_PROTOCOL.VersionPacket.CLASSID:b=new FB_PROTOCOL.VersionPacket();
b.load(a);
return b;
case FB_PROTOCOL.GameVersionPacket.CLASSID:b=new FB_PROTOCOL.GameVersionPacket();
b.load(a);
return b;
case FB_PROTOCOL.GoodPacket.CLASSID:b=new FB_PROTOCOL.GoodPacket();
b.load(a);
return b;
case FB_PROTOCOL.BadPacket.CLASSID:b=new FB_PROTOCOL.BadPacket();
b.load(a);
return b;
case FB_PROTOCOL.SystemMessagePacket.CLASSID:b=new FB_PROTOCOL.SystemMessagePacket();
b.load(a);
return b;
case FB_PROTOCOL.Param.CLASSID:b=new FB_PROTOCOL.Param();
b.load(a);
return b;
case FB_PROTOCOL.ParamFilter.CLASSID:b=new FB_PROTOCOL.ParamFilter();
b.load(a);
return b;
case FB_PROTOCOL.PingPacket.CLASSID:b=new FB_PROTOCOL.PingPacket();
b.load(a);
return b;
case FB_PROTOCOL.Attribute.CLASSID:b=new FB_PROTOCOL.Attribute();
b.load(a);
return b;
case FB_PROTOCOL.LoginRequestPacket.CLASSID:b=new FB_PROTOCOL.LoginRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.LoginResponsePacket.CLASSID:b=new FB_PROTOCOL.LoginResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.LogoutPacket.CLASSID:b=new FB_PROTOCOL.LogoutPacket();
b.load(a);
return b;
case FB_PROTOCOL.PlayerInfoPacket.CLASSID:b=new FB_PROTOCOL.PlayerInfoPacket();
b.load(a);
return b;
case FB_PROTOCOL.ForcedLogoutPacket.CLASSID:b=new FB_PROTOCOL.ForcedLogoutPacket();
b.load(a);
return b;
case FB_PROTOCOL.SeatInfoPacket.CLASSID:b=new FB_PROTOCOL.SeatInfoPacket();
b.load(a);
return b;
case FB_PROTOCOL.PlayerQueryRequestPacket.CLASSID:b=new FB_PROTOCOL.PlayerQueryRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.PlayerQueryResponsePacket.CLASSID:b=new FB_PROTOCOL.PlayerQueryResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.SystemInfoRequestPacket.CLASSID:b=new FB_PROTOCOL.SystemInfoRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.SystemInfoResponsePacket.CLASSID:b=new FB_PROTOCOL.SystemInfoResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.JoinRequestPacket.CLASSID:b=new FB_PROTOCOL.JoinRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.JoinResponsePacket.CLASSID:b=new FB_PROTOCOL.JoinResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.WatchRequestPacket.CLASSID:b=new FB_PROTOCOL.WatchRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.WatchResponsePacket.CLASSID:b=new FB_PROTOCOL.WatchResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.UnwatchRequestPacket.CLASSID:b=new FB_PROTOCOL.UnwatchRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.UnwatchResponsePacket.CLASSID:b=new FB_PROTOCOL.UnwatchResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.LeaveRequestPacket.CLASSID:b=new FB_PROTOCOL.LeaveRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.LeaveResponsePacket.CLASSID:b=new FB_PROTOCOL.LeaveResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.TableQueryRequestPacket.CLASSID:b=new FB_PROTOCOL.TableQueryRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.TableQueryResponsePacket.CLASSID:b=new FB_PROTOCOL.TableQueryResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.CreateTableRequestPacket.CLASSID:b=new FB_PROTOCOL.CreateTableRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.CreateTableResponsePacket.CLASSID:b=new FB_PROTOCOL.CreateTableResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.InvitePlayersRequestPacket.CLASSID:b=new FB_PROTOCOL.InvitePlayersRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyInvitedPacket.CLASSID:b=new FB_PROTOCOL.NotifyInvitedPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyJoinPacket.CLASSID:b=new FB_PROTOCOL.NotifyJoinPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyLeavePacket.CLASSID:b=new FB_PROTOCOL.NotifyLeavePacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyRegisteredPacket.CLASSID:b=new FB_PROTOCOL.NotifyRegisteredPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyWatchingPacket.CLASSID:b=new FB_PROTOCOL.NotifyWatchingPacket();
b.load(a);
return b;
case FB_PROTOCOL.KickPlayerPacket.CLASSID:b=new FB_PROTOCOL.KickPlayerPacket();
b.load(a);
return b;
case FB_PROTOCOL.TableChatPacket.CLASSID:b=new FB_PROTOCOL.TableChatPacket();
b.load(a);
return b;
case FB_PROTOCOL.GameTransportPacket.CLASSID:b=new FB_PROTOCOL.GameTransportPacket();
b.load(a);
return b;
case FB_PROTOCOL.ServiceTransportPacket.CLASSID:b=new FB_PROTOCOL.ServiceTransportPacket();
b.load(a);
return b;
case FB_PROTOCOL.LocalServiceTransportPacket.CLASSID:b=new FB_PROTOCOL.LocalServiceTransportPacket();
b.load(a);
return b;
case FB_PROTOCOL.MttTransportPacket.CLASSID:b=new FB_PROTOCOL.MttTransportPacket();
b.load(a);
return b;
case FB_PROTOCOL.EncryptedTransportPacket.CLASSID:b=new FB_PROTOCOL.EncryptedTransportPacket();
b.load(a);
return b;
case FB_PROTOCOL.JoinChatChannelRequestPacket.CLASSID:b=new FB_PROTOCOL.JoinChatChannelRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.JoinChatChannelResponsePacket.CLASSID:b=new FB_PROTOCOL.JoinChatChannelResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.LeaveChatChannelPacket.CLASSID:b=new FB_PROTOCOL.LeaveChatChannelPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifyChannelChatPacket.CLASSID:b=new FB_PROTOCOL.NotifyChannelChatPacket();
b.load(a);
return b;
case FB_PROTOCOL.ChannelChatPacket.CLASSID:b=new FB_PROTOCOL.ChannelChatPacket();
b.load(a);
return b;
case FB_PROTOCOL.LobbyQueryPacket.CLASSID:b=new FB_PROTOCOL.LobbyQueryPacket();
b.load(a);
return b;
case FB_PROTOCOL.TableSnapshotPacket.CLASSID:b=new FB_PROTOCOL.TableSnapshotPacket();
b.load(a);
return b;
case FB_PROTOCOL.TableUpdatePacket.CLASSID:b=new FB_PROTOCOL.TableUpdatePacket();
b.load(a);
return b;
case FB_PROTOCOL.LobbySubscribePacket.CLASSID:b=new FB_PROTOCOL.LobbySubscribePacket();
b.load(a);
return b;
case FB_PROTOCOL.LobbyUnsubscribePacket.CLASSID:b=new FB_PROTOCOL.LobbyUnsubscribePacket();
b.load(a);
return b;
case FB_PROTOCOL.TableRemovedPacket.CLASSID:b=new FB_PROTOCOL.TableRemovedPacket();
b.load(a);
return b;
case FB_PROTOCOL.TournamentSnapshotPacket.CLASSID:b=new FB_PROTOCOL.TournamentSnapshotPacket();
b.load(a);
return b;
case FB_PROTOCOL.TournamentUpdatePacket.CLASSID:b=new FB_PROTOCOL.TournamentUpdatePacket();
b.load(a);
return b;
case FB_PROTOCOL.TournamentRemovedPacket.CLASSID:b=new FB_PROTOCOL.TournamentRemovedPacket();
b.load(a);
return b;
case FB_PROTOCOL.LobbyObjectSubscribePacket.CLASSID:b=new FB_PROTOCOL.LobbyObjectSubscribePacket();
b.load(a);
return b;
case FB_PROTOCOL.LobbyObjectUnsubscribePacket.CLASSID:b=new FB_PROTOCOL.LobbyObjectUnsubscribePacket();
b.load(a);
return b;
case FB_PROTOCOL.TableSnapshotListPacket.CLASSID:b=new FB_PROTOCOL.TableSnapshotListPacket();
b.load(a);
return b;
case FB_PROTOCOL.TableUpdateListPacket.CLASSID:b=new FB_PROTOCOL.TableUpdateListPacket();
b.load(a);
return b;
case FB_PROTOCOL.TournamentSnapshotListPacket.CLASSID:b=new FB_PROTOCOL.TournamentSnapshotListPacket();
b.load(a);
return b;
case FB_PROTOCOL.TournamentUpdateListPacket.CLASSID:b=new FB_PROTOCOL.TournamentUpdateListPacket();
b.load(a);
return b;
case FB_PROTOCOL.FilteredJoinTableRequestPacket.CLASSID:b=new FB_PROTOCOL.FilteredJoinTableRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.FilteredJoinTableResponsePacket.CLASSID:b=new FB_PROTOCOL.FilteredJoinTableResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.FilteredJoinCancelRequestPacket.CLASSID:b=new FB_PROTOCOL.FilteredJoinCancelRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.FilteredJoinCancelResponsePacket.CLASSID:b=new FB_PROTOCOL.FilteredJoinCancelResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.FilteredJoinTableAvailablePacket.CLASSID:b=new FB_PROTOCOL.FilteredJoinTableAvailablePacket();
b.load(a);
return b;
case FB_PROTOCOL.ProbeStamp.CLASSID:b=new FB_PROTOCOL.ProbeStamp();
b.load(a);
return b;
case FB_PROTOCOL.ProbePacket.CLASSID:b=new FB_PROTOCOL.ProbePacket();
b.load(a);
return b;
case FB_PROTOCOL.MttRegisterRequestPacket.CLASSID:b=new FB_PROTOCOL.MttRegisterRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.MttRegisterResponsePacket.CLASSID:b=new FB_PROTOCOL.MttRegisterResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.MttUnregisterRequestPacket.CLASSID:b=new FB_PROTOCOL.MttUnregisterRequestPacket();
b.load(a);
return b;
case FB_PROTOCOL.MttUnregisterResponsePacket.CLASSID:b=new FB_PROTOCOL.MttUnregisterResponsePacket();
b.load(a);
return b;
case FB_PROTOCOL.MttSeatedPacket.CLASSID:b=new FB_PROTOCOL.MttSeatedPacket();
b.load(a);
return b;
case FB_PROTOCOL.MttPickedUpPacket.CLASSID:b=new FB_PROTOCOL.MttPickedUpPacket();
b.load(a);
return b;
case FB_PROTOCOL.NotifySeatedPacket.CLASSID:b=new FB_PROTOCOL.NotifySeatedPacket();
b.load(a);
return b
}return null
};FB_PROTOCOL.ResponseStatusEnum=function(){};
FB_PROTOCOL.ResponseStatusEnum.OK=0;
FB_PROTOCOL.ResponseStatusEnum.FAILED=1;
FB_PROTOCOL.ResponseStatusEnum.DENIED=2;
FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.ResponseStatusEnum.OK;
case 1:return FB_PROTOCOL.ResponseStatusEnum.FAILED;
case 2:return FB_PROTOCOL.ResponseStatusEnum.DENIED
}return -1
};FB_PROTOCOL.SeatInfoPacket=function(){this.classId=function(){return FB_PROTOCOL.SeatInfoPacket.CLASSID
};
this.tableid={};
this.seat={};
this.status={};
this.player={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeByte(this.seat);
a.writeUnsignedByte(this.status);
a.writeArray(this.player.save());
return a
};
this.load=function(a){this.tableid=a.readInt();
this.seat=a.readByte();
this.status=FB_PROTOCOL.PlayerStatusEnum.makePlayerStatusEnum(a.readUnsignedByte());
this.player=new FB_PROTOCOL.PlayerInfoPacket();
this.player.load(a)
};
this.toString=function(){var a="FB_PROTOCOL.SeatInfoPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seat["+this.seat.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" player["+this.player.toString()+"]";
return a
}
};
FB_PROTOCOL.SeatInfoPacket.CLASSID=15;FB_PROTOCOL.ServiceIdentifierEnum=function(){};
FB_PROTOCOL.ServiceIdentifierEnum.NAMESPACE=0;
FB_PROTOCOL.ServiceIdentifierEnum.CONTRACT=1;
FB_PROTOCOL.ServiceIdentifierEnum.makeServiceIdentifierEnum=function(a){switch(a){case 0:return FB_PROTOCOL.ServiceIdentifierEnum.NAMESPACE;
case 1:return FB_PROTOCOL.ServiceIdentifierEnum.CONTRACT
}return -1
};FB_PROTOCOL.ServiceTransportPacket=function(){this.classId=function(){return FB_PROTOCOL.ServiceTransportPacket.CLASSID
};
this.pid={};
this.seq={};
this.service={};
this.idtype={};
this.servicedata=[];
this.attributes=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.pid);
a.writeInt(this.seq);
a.writeString(this.service);
a.writeByte(this.idtype);
a.writeInt(this.servicedata.length);
a.writeArray(this.servicedata);
a.writeInt(this.attributes.length);
var b;
for(b=0;
b<this.attributes.length;
b++){a.writeArray(this.attributes[b].save())
}return a
};
this.load=function(a){this.pid=a.readInt();
this.seq=a.readInt();
this.service=a.readString();
this.idtype=a.readByte();
var b=a.readInt();
this.servicedata=a.readArray(b);
var c;
var d=a.readInt();
var e;
this.attributes=[];
for(c=0;
c<d;
c++){e=new FB_PROTOCOL.Attribute();
e.load(a);
this.attributes.push(e)
}};
this.toString=function(){var a="FB_PROTOCOL.ServiceTransportPacket :";
a+=" pid["+this.pid.toString()+"]";
a+=" seq["+this.seq.toString()+"]";
a+=" service["+this.service.toString()+"]";
a+=" idtype["+this.idtype.toString()+"]";
a+=" servicedata["+this.servicedata.toString()+"]";
a+=" attributes["+this.attributes.toString()+"]";
return a
}
};
FB_PROTOCOL.ServiceTransportPacket.CLASSID=101;FB_PROTOCOL.SystemInfoRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.SystemInfoRequestPacket.CLASSID
};
this.save=function(){return[]
};
this.load=function(a){};
this.toString=function(){var a="FB_PROTOCOL.SystemInfoRequestPacket :";
return a
}
};
FB_PROTOCOL.SystemInfoRequestPacket.CLASSID=18;FB_PROTOCOL.SystemInfoResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.SystemInfoResponsePacket.CLASSID
};
this.players={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.players);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.players=a.readInt();
var b;
var d=a.readInt();
var c;
this.params=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.params.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.SystemInfoResponsePacket :";
a+=" players["+this.players.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.SystemInfoResponsePacket.CLASSID=19;FB_PROTOCOL.SystemMessagePacket=function(){this.classId=function(){return FB_PROTOCOL.SystemMessagePacket.CLASSID
};
this.type={};
this.level={};
this.message={};
this.pids=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.type);
a.writeInt(this.level);
a.writeString(this.message);
a.writeInt(this.pids.length);
var b;
for(b=0;
b<this.pids.length;
b++){a.writeInt(this.pids[b])
}return a
};
this.load=function(a){this.type=a.readInt();
this.level=a.readInt();
this.message=a.readString();
var b;
var c=a.readInt();
this.pids=[];
for(b=0;
b<c;
b++){this.pids.push(a.readInt())
}};
this.toString=function(){var a="FB_PROTOCOL.SystemMessagePacket :";
a+=" type["+this.type.toString()+"]";
a+=" level["+this.level.toString()+"]";
a+=" message["+this.message.toString()+"]";
a+=" pids["+this.pids.toString()+"]";
return a
}
};
FB_PROTOCOL.SystemMessagePacket.CLASSID=4;FB_PROTOCOL.TableChatPacket=function(){this.classId=function(){return FB_PROTOCOL.TableChatPacket.CLASSID
};
this.tableid={};
this.pid={};
this.message={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeInt(this.pid);
a.writeString(this.message);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.pid=a.readInt();
this.message=a.readString()
};
this.toString=function(){var a="FB_PROTOCOL.TableChatPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" pid["+this.pid.toString()+"]";
a+=" message["+this.message.toString()+"]";
return a
}
};
FB_PROTOCOL.TableChatPacket.CLASSID=80;FB_PROTOCOL.TableQueryRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.TableQueryRequestPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.TableQueryRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.TableQueryRequestPacket.CLASSID=38;FB_PROTOCOL.TableQueryResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.TableQueryResponsePacket.CLASSID
};
this.tableid={};
this.status={};
this.seats=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeUnsignedByte(this.status);
a.writeInt(this.seats.length);
var b;
for(b=0;
b<this.seats.length;
b++){a.writeArray(this.seats[b].save())
}return a
};
this.load=function(a){this.tableid=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte());
var b;
var d=a.readInt();
var c;
this.seats=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.SeatInfoPacket();
c.load(a);
this.seats.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.TableQueryResponsePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" status["+this.status.toString()+"]";
a+=" seats["+this.seats.toString()+"]";
return a
}
};
FB_PROTOCOL.TableQueryResponsePacket.CLASSID=39;FB_PROTOCOL.TableRemovedPacket=function(){this.classId=function(){return FB_PROTOCOL.TableRemovedPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.TableRemovedPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.TableRemovedPacket.CLASSID=147;FB_PROTOCOL.TableSnapshotListPacket=function(){this.classId=function(){return FB_PROTOCOL.TableSnapshotListPacket.CLASSID
};
this.snapshots=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.snapshots.length);
var b;
for(b=0;
b<this.snapshots.length;
b++){a.writeArray(this.snapshots[b].save())
}return a
};
this.load=function(a){var b;
var d=a.readInt();
var c;
this.snapshots=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.TableSnapshotPacket();
c.load(a);
this.snapshots.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.TableSnapshotListPacket :";
a+=" snapshots["+this.snapshots.toString()+"]";
return a
}
};
FB_PROTOCOL.TableSnapshotListPacket.CLASSID=153;FB_PROTOCOL.TableSnapshotPacket=function(){this.classId=function(){return FB_PROTOCOL.TableSnapshotPacket.CLASSID
};
this.tableid={};
this.address={};
this.name={};
this.capacity={};
this.seated={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeString(this.address);
a.writeString(this.name);
a.writeShort(this.capacity);
a.writeShort(this.seated);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.tableid=a.readInt();
this.address=a.readString();
this.name=a.readString();
this.capacity=a.readShort();
this.seated=a.readShort();
var b;
var d=a.readInt();
var c;
this.params=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.params.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.TableSnapshotPacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" name["+this.name.toString()+"]";
a+=" capacity["+this.capacity.toString()+"]";
a+=" seated["+this.seated.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.TableSnapshotPacket.CLASSID=143;FB_PROTOCOL.TableUpdateListPacket=function(){this.classId=function(){return FB_PROTOCOL.TableUpdateListPacket.CLASSID
};
this.updates=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.updates.length);
var b;
for(b=0;
b<this.updates.length;
b++){a.writeArray(this.updates[b].save())
}return a
};
this.load=function(a){var c;
var d=a.readInt();
var b;
this.updates=[];
for(c=0;
c<d;
c++){b=new FB_PROTOCOL.TableUpdatePacket();
b.load(a);
this.updates.push(b)
}};
this.toString=function(){var a="FB_PROTOCOL.TableUpdateListPacket :";
a+=" updates["+this.updates.toString()+"]";
return a
}
};
FB_PROTOCOL.TableUpdateListPacket.CLASSID=154;FB_PROTOCOL.TableUpdatePacket=function(){this.classId=function(){return FB_PROTOCOL.TableUpdatePacket.CLASSID
};
this.tableid={};
this.seated={};
this.params=[];
this.removedParams=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeShort(this.seated);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}a.writeInt(this.removedParams.length);
for(b=0;
b<this.removedParams.length;
b++){a.writeString(this.removedParams[b])
}return a
};
this.load=function(a){this.tableid=a.readInt();
this.seated=a.readShort();
var c;
var e=a.readInt();
var d;
this.params=[];
for(c=0;
c<e;
c++){d=new FB_PROTOCOL.Param();
d.load(a);
this.params.push(d)
}var b=a.readInt();
this.removedParams=[];
for(c=0;
c<b;
c++){this.removedParams.push(a.readString())
}};
this.toString=function(){var a="FB_PROTOCOL.TableUpdatePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" seated["+this.seated.toString()+"]";
a+=" params["+this.params.toString()+"]";
a+=" removed_params["+this.removedParams.toString()+"]";
return a
}
};
FB_PROTOCOL.TableUpdatePacket.CLASSID=144;FB_PROTOCOL.TournamentAttributesEnum=function(){};
FB_PROTOCOL.TournamentAttributesEnum.NAME=0;
FB_PROTOCOL.TournamentAttributesEnum.CAPACITY=1;
FB_PROTOCOL.TournamentAttributesEnum.REGISTERED=2;
FB_PROTOCOL.TournamentAttributesEnum.ACTIVE_PLAYERS=3;
FB_PROTOCOL.TournamentAttributesEnum.STATUS=4;
FB_PROTOCOL.TournamentAttributesEnum.makeTournamentAttributesEnum=function(a){switch(a){case 0:return FB_PROTOCOL.TournamentAttributesEnum.NAME;
case 1:return FB_PROTOCOL.TournamentAttributesEnum.CAPACITY;
case 2:return FB_PROTOCOL.TournamentAttributesEnum.REGISTERED;
case 3:return FB_PROTOCOL.TournamentAttributesEnum.ACTIVE_PLAYERS;
case 4:return FB_PROTOCOL.TournamentAttributesEnum.STATUS
}return -1
};FB_PROTOCOL.TournamentRegisterResponseStatusEnum=function(){};
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.OK=0;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.FAILED=1;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED=2;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_LOW_FUNDS=3;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_MTT_FULL=4;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_NO_ACCESS=5;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_ALREADY_REGISTERED=6;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_TOURNAMENT_RUNNING=7;
FB_PROTOCOL.TournamentRegisterResponseStatusEnum.makeTournamentRegisterResponseStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.OK;
case 1:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.FAILED;
case 2:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED;
case 3:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_LOW_FUNDS;
case 4:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_MTT_FULL;
case 5:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_NO_ACCESS;
case 6:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_ALREADY_REGISTERED;
case 7:return FB_PROTOCOL.TournamentRegisterResponseStatusEnum.DENIED_TOURNAMENT_RUNNING
}return -1
};FB_PROTOCOL.TournamentRemovedPacket=function(){this.classId=function(){return FB_PROTOCOL.TournamentRemovedPacket.CLASSID
};
this.mttid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
return a
};
this.load=function(a){this.mttid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.TournamentRemovedPacket :";
a+=" mttid["+this.mttid.toString()+"]";
return a
}
};
FB_PROTOCOL.TournamentRemovedPacket.CLASSID=150;FB_PROTOCOL.TournamentSnapshotListPacket=function(){this.classId=function(){return FB_PROTOCOL.TournamentSnapshotListPacket.CLASSID
};
this.snapshots=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.snapshots.length);
var b;
for(b=0;
b<this.snapshots.length;
b++){a.writeArray(this.snapshots[b].save())
}return a
};
this.load=function(a){var b;
var c=a.readInt();
var d;
this.snapshots=[];
for(b=0;
b<c;
b++){d=new FB_PROTOCOL.TournamentSnapshotPacket();
d.load(a);
this.snapshots.push(d)
}};
this.toString=function(){var a="FB_PROTOCOL.TournamentSnapshotListPacket :";
a+=" snapshots["+this.snapshots.toString()+"]";
return a
}
};
FB_PROTOCOL.TournamentSnapshotListPacket.CLASSID=155;FB_PROTOCOL.TournamentSnapshotPacket=function(){this.classId=function(){return FB_PROTOCOL.TournamentSnapshotPacket.CLASSID
};
this.mttid={};
this.address={};
this.params=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeString(this.address);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}return a
};
this.load=function(a){this.mttid=a.readInt();
this.address=a.readString();
var b;
var d=a.readInt();
var c;
this.params=[];
for(b=0;
b<d;
b++){c=new FB_PROTOCOL.Param();
c.load(a);
this.params.push(c)
}};
this.toString=function(){var a="FB_PROTOCOL.TournamentSnapshotPacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" address["+this.address.toString()+"]";
a+=" params["+this.params.toString()+"]";
return a
}
};
FB_PROTOCOL.TournamentSnapshotPacket.CLASSID=148;FB_PROTOCOL.TournamentUpdateListPacket=function(){this.classId=function(){return FB_PROTOCOL.TournamentUpdateListPacket.CLASSID
};
this.updates=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.updates.length);
var b;
for(b=0;
b<this.updates.length;
b++){a.writeArray(this.updates[b].save())
}return a
};
this.load=function(a){var b;
var c=a.readInt();
var d;
this.updates=[];
for(b=0;
b<c;
b++){d=new FB_PROTOCOL.TournamentUpdatePacket();
d.load(a);
this.updates.push(d)
}};
this.toString=function(){var a="FB_PROTOCOL.TournamentUpdateListPacket :";
a+=" updates["+this.updates.toString()+"]";
return a
}
};
FB_PROTOCOL.TournamentUpdateListPacket.CLASSID=156;FB_PROTOCOL.TournamentUpdatePacket=function(){this.classId=function(){return FB_PROTOCOL.TournamentUpdatePacket.CLASSID
};
this.mttid={};
this.params=[];
this.removedParams=[];
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.mttid);
a.writeInt(this.params.length);
var b;
for(b=0;
b<this.params.length;
b++){a.writeArray(this.params[b].save())
}a.writeInt(this.removedParams.length);
for(b=0;
b<this.removedParams.length;
b++){a.writeString(this.removedParams[b])
}return a
};
this.load=function(a){this.mttid=a.readInt();
var c;
var e=a.readInt();
var d;
this.params=[];
for(c=0;
c<e;
c++){d=new FB_PROTOCOL.Param();
d.load(a);
this.params.push(d)
}var b=a.readInt();
this.removedParams=[];
for(c=0;
c<b;
c++){this.removedParams.push(a.readString())
}};
this.toString=function(){var a="FB_PROTOCOL.TournamentUpdatePacket :";
a+=" mttid["+this.mttid.toString()+"]";
a+=" params["+this.params.toString()+"]";
a+=" removed_params["+this.removedParams.toString()+"]";
return a
}
};
FB_PROTOCOL.TournamentUpdatePacket.CLASSID=149;FB_PROTOCOL.UnwatchRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.UnwatchRequestPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.UnwatchRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.UnwatchRequestPacket.CLASSID=34;FB_PROTOCOL.UnwatchResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.UnwatchResponsePacket.CLASSID
};
this.tableid={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.status=FB_PROTOCOL.ResponseStatusEnum.makeResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.UnwatchResponsePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.UnwatchResponsePacket.CLASSID=35;FB_PROTOCOL.VersionPacket=function(){this.classId=function(){return FB_PROTOCOL.VersionPacket.CLASSID
};
this.game={};
this.operatorid={};
this.protocol={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.game);
a.writeInt(this.operatorid);
a.writeInt(this.protocol);
return a
};
this.load=function(a){this.game=a.readInt();
this.operatorid=a.readInt();
this.protocol=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.VersionPacket :";
a+=" game["+this.game.toString()+"]";
a+=" operatorid["+this.operatorid.toString()+"]";
a+=" protocol["+this.protocol.toString()+"]";
return a
}
};
FB_PROTOCOL.VersionPacket.CLASSID=0;FB_PROTOCOL.WatchRequestPacket=function(){this.classId=function(){return FB_PROTOCOL.WatchRequestPacket.CLASSID
};
this.tableid={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
return a
};
this.load=function(a){this.tableid=a.readInt()
};
this.toString=function(){var a="FB_PROTOCOL.WatchRequestPacket :";
a+=" tableid["+this.tableid.toString()+"]";
return a
}
};
FB_PROTOCOL.WatchRequestPacket.CLASSID=32;FB_PROTOCOL.WatchResponsePacket=function(){this.classId=function(){return FB_PROTOCOL.WatchResponsePacket.CLASSID
};
this.tableid={};
this.status={};
this.save=function(){var a=new FIREBASE.ByteArray();
a.writeInt(this.tableid);
a.writeUnsignedByte(this.status);
return a
};
this.load=function(a){this.tableid=a.readInt();
this.status=FB_PROTOCOL.WatchResponseStatusEnum.makeWatchResponseStatusEnum(a.readUnsignedByte())
};
this.toString=function(){var a="FB_PROTOCOL.WatchResponsePacket :";
a+=" tableid["+this.tableid.toString()+"]";
a+=" status["+this.status.toString()+"]";
return a
}
};
FB_PROTOCOL.WatchResponsePacket.CLASSID=33;FB_PROTOCOL.WatchResponseStatusEnum=function(){};
FB_PROTOCOL.WatchResponseStatusEnum.OK=0;
FB_PROTOCOL.WatchResponseStatusEnum.FAILED=1;
FB_PROTOCOL.WatchResponseStatusEnum.DENIED=2;
FB_PROTOCOL.WatchResponseStatusEnum.DENIED_ALREADY_SEATED=3;
FB_PROTOCOL.WatchResponseStatusEnum.makeWatchResponseStatusEnum=function(a){switch(a){case 0:return FB_PROTOCOL.WatchResponseStatusEnum.OK;
case 1:return FB_PROTOCOL.WatchResponseStatusEnum.FAILED;
case 2:return FB_PROTOCOL.WatchResponseStatusEnum.DENIED;
case 3:return FB_PROTOCOL.WatchResponseStatusEnum.DENIED_ALREADY_SEATED
}return -1
};