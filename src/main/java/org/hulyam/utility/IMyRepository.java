package org.hulyam.utility;

/**
 * İlişkisel veya ilişkisel olmayan tüm DB yapılarında kullanılmak üzere genişletilebilir
 * olan bu interface kullanılacaktır.
 *
 * @param <T>  : Entity için tip belirtir. (Musteri,Urun vs..)
 * @param <ID> : Entity içinde @ID ile işaretlenmiş alanın tipi (Integer,Long,String)
 */
public interface IMyRepository<T, ID> {
}
