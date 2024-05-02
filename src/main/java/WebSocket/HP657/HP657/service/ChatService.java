package WebSocket.HP657.HP657.service;

import WebSocket.HP657.HP657.dto.ChatMessageDto;
import jakarta.transaction.Transactional;
import org.springframework.web.socket.WebSocketSession;

@Transactional
public void saveMessage(ChatMessageDto chatMessageDto) {
    String email = jwtUtils.getEmailFromToken(chatMessageDto.getToken());
    Members members = membersRepository.findByEmail(email).get();
    ChattingRoom chattingRoom = chattingRoomRepository.findById(chatMessageDto.getRoomId());
    ChattingMessage chattingMessage = new ChattingMessage(chatMessageDto, chattingRoom, members);

    chattingMessageRepository.save(chattingMessage);
    members.addChattingMessage(chattingMessage);
    chattingRoom.addChattingMessage(chattingMessage);
}

public <T> void sendMessage(WebSocketSession session, T message) {
    try {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    } catch (IOException e) {
        throw new ApiException(HttpStatus.BAD_GATEWAY, "입출력 오류");
    }
}